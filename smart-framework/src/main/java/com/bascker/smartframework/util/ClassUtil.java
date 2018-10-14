package com.bascker.smartframework.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.jar.JarFile;

/**
 * 类操作工具类
 *
 * @author bascker
 * @since 1.0.0
 */
public class ClassUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    private static final String PROTOCOL_FILE = "file";
    private static final String PROTOCOL_JAR = "jar";

    /**
     * 获取类加载器
     * @return
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类，默认不初始化类的静态代码块，以提高加载类的性能
     * @param className
     * @return
     */
    public static Class<?> loadClass(final String className) {
        return loadClass(className, false);
    }

    /**
     * 加载类
     * @param className         类名
     * @param isInitialized     是否执行类的静态代码块
     * @return
     */
    public static Class<?> loadClass(final String className, final boolean isInitialized) {
        LOGGER.info("[smart] load {}, initialized = {}", className, isInitialized);
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class failure", e);
            throw new RuntimeException(e);
        }

        return clazz;
    }

    /**
     * 获取指定包名下的所有类
     * @param packageName   包名
     * @return
     */
    public static Set<Class<?>> getClasses(final String packageName) {
        LOGGER.debug("[smart] start getClasses, packageName = {}", packageName);

        final Set<Class<?>> classSet = new HashSet<>();
        try {
            final Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                final URL url = urls.nextElement();
                if (Objects.nonNull(url)) {
                    LOGGER.debug("[smart] url = {}", url);
                    final String protocol = url.getProtocol();
                    if (PROTOCOL_FILE.equals(protocol)) {
                        final String packagePath = url.getPath().replaceAll("%20", " ");
                        addClass(classSet, packagePath, packageName);
                    } else if (PROTOCOL_JAR.equals(protocol)) {
                        // 从 jae 包中读取文件
                        final JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (Objects.nonNull(jarURLConnection)) {
                            final JarFile jarFile = jarURLConnection.getJarFile();
                            if (Objects.nonNull(jarFile)) {
                                jarFile.stream().forEach(jarEntry -> {
                                    final String entryName = jarEntry.getName();
                                    if (StringUtils.endsWith(entryName, ".class")) {
                                        final String className = entryName.substring(0, entryName.lastIndexOf("."))
                                                .replaceAll("/", ".");
                                        doAddClass(classSet, className);
                                    }
                                });
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("get class set failure", e);
            throw new RuntimeException(e);
        }

        return classSet;
    }

    /**
     * 加载一个 class 对象，并加入集合中
     * @param classSet
     * @param className
     */
    private static void doAddClass(final Set<Class<?>> classSet, final String className) {
        final Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }

    /**
     * 将某个包下的所有 class 文件加入集合中
     * @param classSet
     * @param packagePath
     * @param packageName
     */
    private static void addClass(final Set<Class<?>> classSet, final String packagePath, final String packageName) {
        LOGGER.debug("[smart] addClass start, packageName is {}, packagePath is {}", packageName, packagePath);
        final File[] files = new File(packagePath).listFiles(file ->
                (file.isFile() && StringUtils.endsWith(file.getName(), ".class")) || file.isDirectory());
        Arrays.stream(files).forEach(file -> {
            final String filename = file.getName();
            LOGGER.debug("[smart] filename is {}", filename);
            if (file.isFile()) {
                String className = filename.substring(0, filename.lastIndexOf("."));
                if (StringUtils.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }

                LOGGER.debug("[smart] add {} to set", className);
                doAddClass(classSet, className);
            } else {
                String subPackagePath = filename;
                if (StringUtils.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = filename;
                if (StringUtils.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                LOGGER.debug("[smart] subPackagePath is {}", subPackagePath);
                // 递归
                addClass(classSet, subPackagePath, subPackageName);
            }
        });
    }

    private ClassUtil() {}

}
