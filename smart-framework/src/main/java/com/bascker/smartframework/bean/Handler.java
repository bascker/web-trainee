package com.bascker.smartframework.bean;

import java.lang.reflect.Method;

/**
 * 封装 Action 信息
 *
 * @author bascker
 * @since 1.0.0
 */
public class Handler {

    /**
     * Controller 类
     */
    private Class<?> controllerClasss;

    /**
     * Action 方法
     */
    private Method actionMethod;

    public Handler(final Class<?> controllerClasss, final Method actionMethod) {
        this.controllerClasss = controllerClasss;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClasss() {
        return controllerClasss;
    }

    public Method getActionMethod() {
        return actionMethod;
    }

    public void setControllerClasss(final Class<?> controllerClasss) {
        this.controllerClasss = controllerClasss;
    }

    public void setActionMethod(final Method actionMethod) {
        this.actionMethod = actionMethod;
    }

    @Override
    public String toString() {
        return "Handler{" +
                "controllerClasss=" + controllerClasss +
                ", actionMethod=" + actionMethod +
                '}';
    }
}
