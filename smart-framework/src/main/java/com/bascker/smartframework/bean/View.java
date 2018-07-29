package com.bascker.smartframework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 视图对象, 包含视图路径和数据所需的模型数据
 *
 * @author bascker
 * @since 1.0.0
 */
public class View {

    /**
     * 视图路径
     */
    private String path;

    /**
     * 模型数据
     */
    private Map<String, Object> model;

    public View(final String path) {
        this.path = path;
        this.model = new HashMap<>();
    }

    public View addAttribute(final String key, final Object value) {
        model.put(key, value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    @Override
    public String toString() {
        return "View{" +
                "path='" + path + '\'' +
                ", model=" + model +
                '}';
    }
}