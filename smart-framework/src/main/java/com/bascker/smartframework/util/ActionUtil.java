package com.bascker.smartframework.util;

import com.bascker.smartframework.bean.Request;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Action 注解的工具类
 *
 * @author bascker
 * @since 1.0.0
 */
public class ActionUtil {


    /**
     * Action 注解 value 解析成数组后的大小
     */
    private static final int ACTION_VALUE_ARR_SIZE = 2;

    /**
     * 将 Action Value 转成数组
     * @param value
     * @return
     */
    public static String[] toArray(final String value) {
        return StringUtils.split(value, ":");
    }

    /**
     * 将 Action Value 转成 Request 对象
     * @param valueArray
     * @return
     */
    public static Request toRequest(final String[] valueArray) {
        final String reqMethod = valueArray[0];
        final String reqPath = valueArray[1];
        return new Request(reqMethod, reqPath);
    }

    /**
     * 将 Action Value 转成 Request 对象
     * @param value
     * @return
     */
    public static Request toRequest(final String value) {
        final String[] array = toArray(value);
        if (isValidValueArray(array)) {
            return toRequest(array);
        } else {
            throw new IllegalArgumentException("Action Value to Request failure, value " + value + " is illegal");
        }
    }

    /**
     * 校验 Action Value 值的合法性
     * @param value
     * @return
     */
    public static boolean isValidValue(final String value) {
        final String[] array = toArray(value);
        return isValidValueArray(array);
    }

    /**
     * 校验 Action Value 数组的合法性
     * @param valueArray
     * @return
     */
    public static boolean isValidValueArray(final String[] valueArray) {
        return ArrayUtils.isNotEmpty(valueArray) && ACTION_VALUE_ARR_SIZE == valueArray.length;
    }

}
