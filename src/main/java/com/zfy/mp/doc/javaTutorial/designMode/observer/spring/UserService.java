package com.zfy.mp.doc.javaTutorial.designMode.observer.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * 用户服务
 */
@Service
public class UserService {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * 注册用户
     */
    public void registerUser(String username, String email) {
        System.out.println("🔐 用户注册中: " + username);

        // 执行注册逻辑...
        System.out.println("✅ 注册成功！");

        // ── 发布事件，所有监听器都会收到通知 ──
        UserRegisteredEvent event = new UserRegisteredEvent(this, username, email);
        eventPublisher.publishEvent(event);
    }
}
