package com.zfy.mp.doc.javaTutorial.designMode.proxy.cglibproxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 事务拦截器
 */
public class TransactionInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        String methodName = method.getName();

        // 开启事务
        System.out.println("\n" + "─".repeat(50));
        System.out.println("🔐 [事务开始] " + methodName);
        System.out.println("─".repeat(50));

        try {
            // 执行目标方法
            Object result = proxy.invokeSuper(obj, args);

            // 提交事务
            System.out.println("─".repeat(50));
            System.out.println("✅ [事务提交] " + methodName);
            System.out.println("─".repeat(50) + "\n");

            return result;
        } catch (Exception e) {
            // 回滚事务
            System.out.println("─".repeat(50));
            System.out.println("❌ [事务回滚] " + methodName);
            System.out.println("   错误: " + e.getMessage());
            System.out.println("─".repeat(50) + "\n");
            throw e;
        }
    }
}
