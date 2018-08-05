package com.bascker.smartframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 反射工具类
 *
 * @author bascker
 */
public class ReflectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T newInstance(final Class<T> clazz) {
        if (Objects.isNull(clazz)) {
            LOGGER.error("clazz is null");
            return null;
        }

        T instance = null;
        try {
            instance = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error("new instance failure", e);
        }

        return instance;
    }

    /**
     * 调用方法
     * @param obj
     * @param method
     * @param args
     * @return
     */
    public static Object invokeMethod(final Object obj, final Method method, final Object... args) {
        if (Objects.isNull(method)) {
            LOGGER.error("method is null");
            return null;
        }

        Object result = null;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("invoke method failure", e);
        }

        return result;
    }

    /**
     * 设置成员变量的值
     * @param obj
     * @param field
     * @param value
     */
    public static void setField(final Object obj, final Field field, final Object value) {
        if (Objects.isNull(field)) {
            LOGGER.error("field is null");
        }

        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            LOGGER.error("set field failure", e);
        }
    }

    private ReflectionUtil() {}

}
