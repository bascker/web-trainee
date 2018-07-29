package com.bascker.smartframework.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 请求信息实体
 *
 * @author bascker
 * @since 1.0.0
 */
public class Request {

    /**
     * 请求方法
     */
    private Method method;

    /**
     * 请求路径
     */
    private String path;

    public Request(final String method, final String path) {
        this.method = Method.getInstance(method);
        this.path = path;
    }

    public Request(final Method method, final String path) {
        this.method = method;
        this.path = path;
    }

    // -------------------------------
    // Getter/Setter
    // -------------------------------

    public Method getMethod() {
        return method;
    }

    public void setMethod(final Method method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    @Override
    public boolean equals(final Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return "Request{" +
                "method=" + method +
                ", path='" + path + '\'' +
                '}';
    }
}
