package com.bascker.smartframework.helper;

import com.bascker.smartframework.annotation.Inject;
import com.bascker.smartframework.util.ReflectionUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * IOC 助手类，进行依赖注入
 *
 * @author bascker
 * @since  1.0.0
 */
public class IocHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(IocHelper.class);

    static {
        // 获取所有 Bean 类和 Bean 实例之间的映射关系（简称 Bean Map）
        final Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionHelper.isNotEmpty(beanMap)) {
            beanMap.forEach((beanClass, beanInstance) -> {
                // 获取 Bean 类定义的所有成员变量（简称 Bean Field）
                final Field[] beanFields = beanClass.getFields();
                if (ArrayUtils.isNotEmpty(beanFields)) {
                    Arrays.stream(beanFields).forEach(beanField -> {
                        // 判断当前成员变量是否带有 @Inject
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            // 获取 Bean Field 对应的实例
                            final Class<?> beanFieldClass = beanField.getType();
                            final Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (Objects.nonNull(beanFieldInstance)) {
                                // 通过反射初始化 BeanField 的值
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    });
                }
            });
        }
    }

    private IocHelper() {}

}
