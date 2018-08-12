package com.bascker.smartframework.bean;

import org.apache.commons.lang3.StringUtils;

/**
 * 请求方法
 *
 * @author bascker
 * @since 1.0.0
 */
public enum Method {

    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private String method;

    Method(final String method) {
        this.method = method;
    }

    public String getMethod() {
        return this.method;
    }

    public static Method getInstance(final String method) {
        if (StringUtils.isEmpty(method)) {
            throw new IllegalArgumentException("get instance failure, the param method is null or empty");
        }

        switch (StringUtils.upperCase(method)) {
            case "GET":
                return GET;
            case "POST":
                return POST;
            case "PUT":
                return PUT;
            case "DELETE":
                return DELETE;
            default:
                throw new IllegalArgumentException("get instance failure, the param method is " + method);
        }
    }

}
