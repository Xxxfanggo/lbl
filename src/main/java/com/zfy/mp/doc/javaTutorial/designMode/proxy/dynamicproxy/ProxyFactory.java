package com.zfy.mp.doc.javaTutorial.designMode.proxy.dynamicproxy;

import java.lang.reflect.Proxy;

/**
 * 动态代理工厂
 */
public class ProxyFactory {

    /**
     * 创建日志代理
     */
    public static <T> T createLogProxy(T target, Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
            target.getClass().getClassLoader(),
            new Class<?>[] { interfaceClass },
            new LoggingInvocationHandler(target)
        );
    }

    /**
     * 创建权限代理
     */
    public static <T> T createPermissionProxy(T target, Class<T> interfaceClass, String user) {
        return (T) Proxy.newProxyInstance(
            target.getClass().getClassLoader(),
            new Class<?>[] { interfaceClass },
            new PermissionInvocationHandler(target, user)
        );
    }
}
