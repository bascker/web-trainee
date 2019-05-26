package com.bascker.smartframework.aop;

import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 链式代理：
 * 1. 可将多个代理通过一条链串起来，逐一执行。
 * 2. 执行顺序，取决于添加到执行链上的顺序：队列的使用
 *
 * @author bascker
 * @since 1.0.0
 */
public class ProxyChain {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyChain.class);

    /**
     * 目标类
     */
    private final Class<?> targetClass;

    /**
     * 目标对象
     */
    private final Object targetObject;

    /**
     * 目标方法
     */
    private final Method targetMethod;

    /**
     * 方法参数
     */
    private final Object[] methodParams;

    /**
     * 方法代理
     */
    private final MethodProxy methodProxy;

    /**
     * 代理列表
     */
    private List<Proxy> proxies = new ArrayList<>();

    /**
     * 代理索引：作为代理对象的计数器
     */
    private int proxyIndex = 0;

    public ProxyChain(final Class<?> targetClass, final Object targetObject, final Method targetMethod,
                      final Object[] methodParams, final MethodProxy methodProxy, final List<Proxy> proxies) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodParams = methodParams;
        this.methodProxy = methodProxy;
        this.proxies = proxies;
    }

    public Object doProxyChain() throws Throwable {
        Object result;
        /*
         * 当未达到上限时，取出代理对象，并执行 doProxy() 方法，直到达到上限才执行 methodProxy 的 invokeSuper()，
         * 执行目标对象的业务逻辑
         */
        if (proxyIndex < proxies.size()) {
            result = proxies.get(proxyIndex ++).doProxy(this);
        } else {
            result = methodProxy.invokeSuper(targetObject, methodParams);
        }

        return result;
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public MethodProxy getMethodProxy() {
        return methodProxy;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public List<Proxy> getProxies() {
        return proxies;
    }

    public int getProxyIndex() {
        return proxyIndex;
    }
}
