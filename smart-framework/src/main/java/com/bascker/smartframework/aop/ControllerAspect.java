package com.bascker.smartframework.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 拦截 Controller 所有方法的切面类
 *
 * @author bascker
 * @since 1.0.0
 */
public class ControllerAspect extends AspectProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);

    private long begin;

    @Override
    public void before(final Class<?> cls, final Method method, final Object[] params) {
        LOGGER.debug("--------------------- begin ---------------------");
        LOGGER.debug("class: {}, method: {}, beginTime: {}", cls.getName(), method.getName(), System.currentTimeMillis());
    }

    @Override
    public void after(final Class<?> cls, final Method method, final Object[] params) {
        LOGGER.debug("costTime: {}", System.currentTimeMillis() - begin);
        LOGGER.debug("---------------------- end ----------------------");
    }
}
