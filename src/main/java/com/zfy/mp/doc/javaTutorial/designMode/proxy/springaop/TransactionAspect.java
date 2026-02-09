package com.zfy.mp.doc.javaTutorial.designMode.proxy.springaop;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 事务切面
 */
@Aspect
@Component
public class TransactionAspect {

    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void beginTransaction() {
        System.out.println("🔐 开始事务");
    }

    @AfterReturning("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void commitTransaction() {
        System.out.println("✅ 提交事务");
    }

    @AfterThrowing("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void rollbackTransaction() {
        System.out.println("❌ 回滚事务");
    }
}
