package com.bascker.smartframework.core;

import com.bascker.smartframework.helper.*;
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
        // 初始化各助手类: AopHelp 需在 IocHelper 之前加载，AopHelper 获取代理对象后，才能通过 IocHelper 进行依赖注入
        final Class<?>[] classes = {
            ClassHelper.class,
            BeanHelper.class,
            AopHelper.class,
            IocHelper.class,
            ControllerHelper.class,
        };

        // TODO: isInitialized 为 false 时，@Inject 存在问题
        Arrays.stream(classes).forEach(clazz -> ClassUtil.loadClass(clazz.getName(), true));
    }

}
