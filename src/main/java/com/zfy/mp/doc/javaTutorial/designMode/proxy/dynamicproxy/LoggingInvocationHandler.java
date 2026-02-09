package com.zfy.mp.doc.javaTutorial.designMode.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 日志调用处理器
 * 在方法调用前后添加日志功能
 */
public class LoggingInvocationHandler implements InvocationHandler {

    private Object target;  // 目标对象

    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 前置处理
        long startTime = System.currentTimeMillis();
        System.out.println("\n" + "─".repeat(50));
        System.out.println("🔔 [调用前] " + method.getName());
        System.out.println("   参数: " + java.util.Arrays.toString(args));
        System.out.println("─".repeat(50));

        try {
            // 调用目标对象的方法
            Object result = method.invoke(target, args);

            // 后置处理
            long endTime = System.currentTimeMillis();
            System.out.println("─".repeat(50));
            System.out.println("✅ [调用后] " + method.getName() + " 完成");
            System.out.println("   返回值: " + result);
            System.out.println("   执行时间: " + (endTime - startTime) + " ms");
            System.out.println("─".repeat(50) + "\n");

            return result;
        } catch (Exception e) {
            // 异常处理
            System.out.println("❌ [异常] " + method.getName() + " 执行失败");
            System.out.println("   异常信息: " + e.getMessage());
            throw e;
        }
    }
}
