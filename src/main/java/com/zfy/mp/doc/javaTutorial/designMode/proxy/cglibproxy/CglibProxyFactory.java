package com.zfy.mp.doc.javaTutorial.designMode.proxy.cglibproxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * CGLIB 代理工厂
 */
public class CglibProxyFactory {

    /**
     * 创建 CGLIB 代理
     */
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(Class<T> targetClass, MethodInterceptor interceptor) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(interceptor);
        return (T) enhancer.create();
    }
}
