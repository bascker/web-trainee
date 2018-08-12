package com.bascker.smartframework.helper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Collection 工具类
 *
 * @author bascker
 * @since 1.0.0
 */
public class CollectionHelper {

    /**
     * 判断 Collection 是否为空
     * @param collection
     * @return
     */
    public static boolean isEmpty(final Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * 判断 Map 是否为空
     * @param map
     * @return
     */
    public static boolean isEmpty(final Map<?, ?> map) {
        return MapUtils.isEmpty(map);
    }

    /**
     * 判断 Collection 是否非空
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(final Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断 Map 是否非空
     * @param map
     * @return
     */
    public static boolean isNotEmpty(final Map<?, ?> map) {
        return !isEmpty(map);
    }

    private CollectionHelper() {}

}
