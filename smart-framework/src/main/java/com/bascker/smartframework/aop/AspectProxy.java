package com.bascker.smartframework.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面代理
 *
 * @author bascker
 * @since 1.0.0
 */
public abstract class AspectProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object doProxy(final ProxyChain proxyChain) throws Throwable {
        Object result = null;

        // 获取目标类
        final Class<?> cls = proxyChain.getTargetClass();
        // 获取目标方法
        final Method method = proxyChain.getTargetMethod();
        // 获取目标方法参数
        final Object[] params = proxyChain.getMethodParams();

        begin();
        try {
            if (!intercept(cls, method, params)) {
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params);
            }
        } catch (Exception e) {
            LOGGER.error("Proxy failure", e);
            error(cls, method, params, e);
            throw e;
        } finally {
            end();
        }

        return result;
    }

    // --------------------------------------------------------
    // 钩子方法：该抽象类中，默认进行空实现，以便子类覆写。若子类没覆写，
    // 也不影响业务逻辑正常运行，相当于不处理。
    // --------------------------------------------------------

    public void begin() {}

    public boolean intercept(final Class<?> cls, final Method method, final Object[] params) throws Throwable {
        return true;
    }

    public void before(final Class<?> cls, final Method method, final Object[] params) {}

    public void after(final Class<?> cls, final Method method, final Object[] params) {}

    public void end() {}

    public void error(final Class<?> cls, final Method method, final Object[] params, Throwable e) {}

}
