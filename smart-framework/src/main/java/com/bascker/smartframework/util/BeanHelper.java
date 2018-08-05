package com.bascker.smartframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 解析获取 Bean 的助手类：基础 Bean 容器
 *
 * @author bascker
 */
public class BeanHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanHelper.class);

    /**
     * 定义 Bean 映射，存放 Bean 类与 Bean 实例之间的关系. key-value: BeanClass-BeanInstance
     */
    public static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        final Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        beanClassSet.forEach(clazz -> {
            final Object obj = ReflectionUtil.newInstance(clazz);
            BEAN_MAP.put(clazz, obj);
        });
    }

    /**
     * 获取 Bean 映射
     * @return
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 根据 Class 获取对应的 Bean 实例
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(final Class<T> clazz) {
        if (!BEAN_MAP.containsKey(clazz)) {
            throw new RuntimeException("can not get bean by class: " + clazz);
        }

        return (T) BEAN_MAP.get(clazz);
    }

    private BeanHelper() {}

}
