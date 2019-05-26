package com.bascker.smartframework.aop;

/**
 * Proxy 接口
 *
 * @author bascker
 * @since 1.0.0
 */
public interface Proxy {

    Object doProxy(final ProxyChain proxyChain) throws Throwable;

}
