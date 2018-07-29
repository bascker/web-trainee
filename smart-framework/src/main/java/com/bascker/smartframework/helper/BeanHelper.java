package com.bascker.smartframework.helper;

import com.bascker.smartframework.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 解析获取 Bean 的助手类：基础 Bean 容器，此时 IoC 容器中的对象都是单例的
 *
 * @author bascker
 * @since 1.0.0
 */
public class BeanHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanHelper.class);

    /**
     * 定义 Bean 映射，存放 Bean 类与 Bean 实例之间的关系. key-value: BeanClass-BeanInstance
     */
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        final Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        LOGGER.debug("beanClassSet size = {}, value is {}", beanClassSet.size(), beanClassSet);
        beanClassSet.forEach(clazz -> {
            LOGGER.debug("new instance {}", clazz);
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
