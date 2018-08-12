package com.bascker.smartframework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Action 方法注解
 *
 * @author bascker
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {

    /**
     * Controller Action 的 value 规则
     */
    String VALUE_REGEX = "\\w+:/\\w*";

    /**
     * 请求类型与路径，key-value 形式, 如 get:/users
     * @return
     */
    String value();

}
