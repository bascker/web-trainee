package com.bascker.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * Properties 文件工具类
 */
public class PropsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载 Properties 属性文件
     * @param filename
     * @return
     */
    public static Properties loadProps(final String filename) {
        Properties props = null;
        // 获取线程上下文中的 ClassLoader，读取 classpath（maven 项目中对应 java 和 resources 两个根目录） 下的文件
        try(InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename)) {
            if (Objects.isNull(is)) {
                throw new FileNotFoundException(filename + " file is not found");
            }
            props = new Properties();
            props.load(is);
        } catch (IOException e) {
            LOGGER.error("load properties file failure", e);
        }

        return props;
    }

    /**
     * 获取字符串属性，默认值为空串
     * @param props
     * @param key
     * @return
     */
    public static String getString(final Properties props, final String key) {
        return getString(props, key, Constant.EMPTY_STR);
    }

    /**
     * 获取字符串属性
     * @param props
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(final Properties props, final String key, String defaultValue) {
        return props.containsKey(key) ? props.getProperty(key) : defaultValue;
    }

    /**
     * 获取数值型属性，默认值为 0
     * @param props
     * @param key
     * @return
     */
    public static int getInt(final Properties props, final String key) {
        return getInt(props, key, 0);
    }

    /**
     * 获取数值型属性
     * @param props
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(final Properties props, final String key, final int defaultValue) {
        return props.containsKey(key) ? CastUtil.castInt(props.getProperty(key)) : defaultValue;
    }

    /**
     * 获取布尔属性值，默认为 false
     * @param props
     * @param key
     * @return
     */
    public static boolean getBoolean(final Properties props, final String key) {
        return getBoolean(props, key, Boolean.FALSE);
    }

    /**
     * 获取布尔属性值
     * @param props
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(final Properties props, final String key, final boolean defaultValue) {
        return props.containsKey(key) ? CastUtil.castBoolean(props.getProperty(key)) : defaultValue;
    }

    private PropsUtil() {}

}
