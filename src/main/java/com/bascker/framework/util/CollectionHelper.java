package com.bascker.framework.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Collection 工具类
 *
 * @author bascker
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

    private CollectionHelper() {}

}
