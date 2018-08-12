package com.bascker.smartframework.bean;

/**
 * 数据对象，包含一个 Object 类型的模型数据，框架将其写入 HttpServletRespone 中，输出到浏览器
 *
 * @author bascker
 * @since 1.0.0
 */
public class Data {

    /**
     * 模型数据
     */
    private Object model;

    public Data(final Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
