package com.bascker.smartframework.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理管理器：提供给切面类使用，在目标方法执行前后添加切面逻辑
 *
 * @author bascker
 * @since 1.0.0
 */
public class ProxyManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyManager.class);

    /**
     * 创建代理对象
     * @param targetClass
     * @param proxies
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxies) {
        // 使用 CGLib 的 Enhancer 来创建代理对象，并将 intercept 的参数传入 ProxyChain 的构造器
        final MethodInterceptor methodInterceptor = (final Object targetObject, final Method targetMethod,
             final Object[] methodParams, final MethodProxy methodProxy) -> new ProxyChain(targetClass, targetObject,
                targetMethod, methodParams, methodProxy, proxies).doProxyChain();

        return (T) Enhancer.create(targetClass, methodInterceptor);
    }

}