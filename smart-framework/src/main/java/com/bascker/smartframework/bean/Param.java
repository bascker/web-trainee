package com.bascker.smartframework.bean;

import com.bascker.smartframework.util.CastUtil;

import java.util.Map;

/**
 * 请求参数对象
 *
 * @author bascker
 * @since 1.0.0
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param(final Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * 根据参数名获取 long 型参数
     * @param name 参数名
     * @return
     */
    public long getLong(final String name) {
        return CastUtil.castLong(paramMap.get(name));
    }

    /**
     * 获取所有字段信息
     * @return
     */
    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    @Override
    public String toString() {
        return "Param{" +
                "paramMap=" + paramMap +
                '}';
    }
}