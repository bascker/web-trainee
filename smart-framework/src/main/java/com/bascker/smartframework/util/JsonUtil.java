package com.bascker.smartframework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Json 工具类
 *
 * @author bascker
 * @since 1.0.0
 */
public class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转 JSON 字符串
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String toJson(final T obj) {
        String json = null;
        try {
            json = MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("convert obj to JSON failure", e);
            throw new RuntimeException(e);
        }

        return json;
    }

    /**
     * 将 JSON 转为 type 类型的对象
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(final String json, final Class<T> type) {
        T obj = null;
        try {
            obj = MAPPER.readValue(json, type);
        } catch (IOException e) {
            LOGGER.error("convert JSON to " + type + " failure", e);
            throw new RuntimeException(e);
        }

        return obj;
    }

}
