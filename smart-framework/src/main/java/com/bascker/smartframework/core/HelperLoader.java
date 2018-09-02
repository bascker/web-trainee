package com.bascker.smartframework.core;

import com.bascker.smartframework.helper.BeanHelper;
import com.bascker.smartframework.helper.ClassHelper;
import com.bascker.smartframework.helper.ControllerHelper;
import com.bascker.smartframework.helper.IocHelper;
import com.bascker.smartframework.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * 初始化程序, 加载框架相关 Helper
 *
 * @author bascker
 * @since 1.0.0
 */
public class HelperLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelperLoader.class);

    public static void init() {
        LOGGER.info("[smart] init smart framework");
        final Class<?>[] classes = {
            ClassHelper.class,
            BeanHelper.class,
            IocHelper.class,
            ControllerHelper.class
        };

        Arrays.stream(classes).forEach(clazz -> ClassUtil.loadClass(clazz.getName()));
    }

}
