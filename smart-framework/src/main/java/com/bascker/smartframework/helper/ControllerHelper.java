package com.bascker.smartframework.helper;

import com.bascker.smartframework.annotation.Action;
import com.bascker.smartframework.bean.Handler;
import com.bascker.smartframework.bean.Request;
import com.bascker.smartframework.util.ActionUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Controller 帮助类
 *
 * @author bascker
 * @since 1.0.0
 */
public class ControllerHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerHelper.class);

    /**
     * 存放请求和处理器的映射关系
     */
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();


    static {
        // 获取所有的 Controller 类
        final Set<Class<?>> controllerClassSet = ClassHelper.getControllerClasses();
        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            controllerClassSet.forEach(controllerClass -> {
                final Method[] methods = controllerClass.getDeclaredMethods();
                if (ArrayUtils.isNotEmpty(methods)) {
                    // 判断当前 Controller 的方法是否带有 @Action 注解
                    Arrays.stream(methods).forEach(method -> {
                        if (method.isAnnotationPresent(Action.class)) {
                            // 解析 @Action 注解，获取 URL 映射规则
                            final Action action = method.getAnnotation(Action.class);
                            final String mapping = action.value();
                            // 验证 URL 映射规则
                            if (mapping.matches(Action.VALUE_REGEX)) {
                                final Request request = ActionUtil.toRequest(mapping);
                                final Handler handler = new Handler(controllerClass, method);

                                // 初始化 Action Map
                                ACTION_MAP.put(request, handler);
                            }
                        }
                    });
                }
            });
        }
    }

    /**
     * 获取 Handler
     * @param requestMethod 请求方法
     * @param requestPath   请求路径
     * @return
     */
    public static Handler getHandler(final String requestMethod, final String requestPath) {
        final Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.getOrDefault(request, null);
    }

}