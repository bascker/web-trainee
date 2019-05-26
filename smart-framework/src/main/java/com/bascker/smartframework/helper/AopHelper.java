package com.bascker.smartframework.helper;

import com.bascker.smartframework.aop.Aspect;
import com.bascker.smartframework.aop.AspectProxy;
import com.bascker.smartframework.aop.Proxy;
import com.bascker.smartframework.aop.ProxyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * AOP 工具类
 * @author bascker
 * @since v1.0
 */
public class AopHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    /**
     * 初始化 AOP 框架
     */
    static {
        // step1：获取代理类与目标集合的映射关系
        final Map<Class<?>, Set<Class<?>>> proxyMap = getProxyMap();
        // step2：获取目标类与代理对象列表的映射关系
        final Map<Class<?>, List<Proxy>> targetMap = getTargetMap(proxyMap);
        // step3：调用 ProxyManager 获取代理对象，并将对象重新放入 BeanMap
        targetMap.forEach((targetClass, proxyList) -> {
            final Object proxy = ProxyManager.createProxy(targetClass, proxyList);
            BeanHelper.setBean(targetClass, proxy);
        });
    }

    /**
     * 获取 Aspect 注解中设置的注解类，若该类不是 Aspect 类，则获取被器注解的相关类
     * @param aspect
     * @return
     */
    private static Set<Class<?>> getTargetClassSet(final Aspect aspect) {
        final Set<Class<?>> targetClasses = new HashSet<>();
        final Class<? extends Annotation> annotation = aspect.value();
        if (Objects.nonNull(annotation) && !annotation.equals(Aspect.class)) {
            targetClasses.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }

        return targetClasses;
    }

    /**
     * 获取代理(切面)类及目标类集合之间的映射关系.:代理类需扩展 {@link AspectProxy} 以及
     * 带有 {@link Aspect} 注解，这样才能根据注解定义的属性去获取注解对应的目标类集合，
     * 从而建立代理类与目标集合之间的映射关系。
     * @return
     */
    private static Map<Class<?>, Set<Class<?>>> getProxyMap() {
        final Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        final Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        proxyClassSet.forEach(proxyClass -> {
            // 若 proxyClass 被 Aspect 注解
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                final Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                final Set<Class<?>> targetClassSet = getTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        });

        return proxyMap;
    }

    /**
     * 获取目标类与代理类之间的映射关系
     * @param proxyMap
     * @return
     */
    private static Map<Class<?>, List<Proxy>> getTargetMap(final Map<Class<?>, Set<Class<?>>> proxyMap) {
        final Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        proxyMap.forEach((proxyClass, targetClassSet) -> {
            targetClassSet.forEach(targetClass -> {
                try {
                    final Proxy proxy = (Proxy) proxyClass.newInstance();
                    if (targetMap.containsKey(targetClass)) {
                        targetMap.get(targetClass).add(proxy);
                    } else {
                        final List<Proxy> proxyList = new ArrayList<>();
                        proxyList.add(proxy);
                        targetMap.put(targetClass, proxyList);
                    }
                } catch (InstantiationException | IllegalAccessException e) {
                    LOGGER.error("new proxy object failure", e);
                }
            });
        });

        return targetMap;
    }

}