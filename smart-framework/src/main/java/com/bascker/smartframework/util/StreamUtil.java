package com.bascker.smartframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * 流操作工具类
 *
 * @author bascker
 * @since 1.0.0
 */
public class StreamUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);

    /**
     * 从输入流中获取字符串
     * @param in 输入流
     * @return
     */
    public static String getString(final InputStream in) {
        final StringBuilder sb = new StringBuilder();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = null;
        try {
            while (Objects.nonNull((line = reader.readLine()))) {
                sb.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("get string failure", e);
            throw new RuntimeException(e);
        }

        return sb.toString();
    }

}
