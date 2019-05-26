package com.bascker.smartframework.helper;

import com.bascker.smartframework.annotation.Controller;
import com.bascker.smartframework.annotation.Repository;
import com.bascker.smartframework.annotation.Service;
import com.bascker.smartframework.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 类操作助手类: 反射工具类
 *
 * @author bascker
 * @since 1.0.0
 */
public class ClassHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassHelper.class);

    /**
     * 定义类集合，用于存放所加载的类
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        final String basePackage = ConfigHelper.getAppBasePackage();
        LOGGER.info("[smart] get all of classes from {}", basePackage);
        CLASS_SET = ClassUtil.getClasses(basePackage);
    }

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取应用包下所有被 {@link Service} 注解的类
     * @return
     */
    public static Set<Class<?>> getServiceClasses() {
        return getAnotationClasses(Service.class);
    }

    /**
     * 获取应用包下所有被 {@link Controller} 注解的类
     * @return
     */
    public static Set<Class<?>> getControllerClasses() {
        return getAnotationClasses(Controller.class);
    }

    /**
     * 获取应用包下所有被 {@link Repository} 注解的类
     * @return
     */
    public static Set<Class<?>> getRepositoryClasses() {
        return getAnotationClasses(Repository.class);
    }

    /**
     * 获取应用包下带指定注解的类，若没有，则返回空集合
     * @param annotationClass
     * @return
     */
    public static Set<Class<?>> getAnotationClasses(final Class annotationClass) {
        if (annotationClass.isAnnotation()) {
            final Set<Class<?>> classSet = new HashSet<>();
            CLASS_SET.forEach(clazz -> {
                if (clazz.isAnnotationPresent(annotationClass)) {
                    classSet.add(clazz);
                }
            });

            return classSet;
        }

        return Collections.emptySet();
    }

    /**
     * 获取应用包下所有 Bean，包括 Service, Controller, Repository, etc.
     * @return
     */
    public static Set<Class<?>> getBeanClassSet() {
        final Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getServiceClasses());
        beanClassSet.addAll(getControllerClasses());
        beanClassSet.addAll(getRepositoryClasses());

        return beanClassSet;
    }

    /**
     * 获取应用包下某父类/接口的所有子类
     * @param superClass 父类/接口
     * @return
     */
    public static Set<Class<?>> getClassSetBySuper(final Class<?> superClass) {
        final Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (isSuperClass(superClass, cls)) {
                classSet.add(cls);
            }
        }

        return classSet;
    }

    /**
     * 获取应用包名下带有某注解的所有类
     * @param annotationClass
     * @return
     */
    public static Set<Class<?>> getClassSetByAnnotation(final Class<? extends Annotation> annotationClass) {
        final Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            // x.isAnnotationPresent(y): 判断类 x 是否被 y 所注解
            if (cls.isAnnotationPresent(annotationClass)) {
                classSet.add(cls);
            }
        }

        return classSet;
    }

    /**
     * 判断类 x 是否是类 y 的父类/接口
     * @param x
     * @param y
     * @return
     */
    private static boolean isSuperClass(Class<?> x, Class<?> y) {
        // x.isAssignableFrom(y): 判断 x 跟 y 是否相同，或者 x 是 y 的父类/接口
        return x.isAssignableFrom(y) && !x.equals(y);
    }

}