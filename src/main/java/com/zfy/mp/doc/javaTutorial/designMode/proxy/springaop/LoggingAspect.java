package com.zfy.mp.doc.javaTutorial.designMode.proxy.springaop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 日志切面
 */
@Aspect
@Component
public class LoggingAspect {

    /**
     * 定义切入点：匹配所有 service 包下的方法
     */
    @Pointcut("execution(* com.zfy.mp.doc.javaTutorial.designMode.proxy.springaop.service.*.*(..))")
    public void serviceMethods() {}

    /**
     * 前置通知：方法执行前
     */
    @Before("serviceMethods()")
    public void beforeMethod() {
        System.out.println("🔔 [前置通知] 方法即将执行");
    }

    /**
     * 后置通知：方法执行后
     */
    @After("serviceMethods()")
    public void afterMethod() {
        System.out.println("✅ [后置通知] 方法执行完成");
    }

    /**
     * 返回通知：方法返回结果后
     */
    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void afterReturning(Object result) {
        System.out.println("📤 [返回通知] 方法返回: " + result);
    }

    /**
     * 异常通知：方法抛出异常时
     */
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void afterThrowing(Exception ex) {
        System.out.println("❌ [异常通知] 方法抛出异常: " + ex.getMessage());
    }

    /**
     * 环绕通知：完全控制方法执行
     */
    @Around("serviceMethods()")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // 执行目标方法
        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        System.out.println("⏱️  [环绕通知] 执行时间: " + (endTime - startTime) + " ms");

        return result;
    }
}
