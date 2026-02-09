package com.zfy.mp.doc.javaTutorial.designMode.observer.spring.multipleEvent;

import com.zfy.mp.doc.javaTutorial.designMode.observer.spring.UserRegisteredEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 增强的用户服务
 * 演示如何发布多种不同的事件
 */
@Service
public class EnhancedUserService {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 用户注册
     */
    public void registerUser(String username, String email) {
        System.out.println("🔐 用户注册中: " + username);
        // 执行注册逻辑...
        System.out.println("✅ 注册成功！");

        // 发布注册事件
        UserRegisteredEvent event = new UserRegisteredEvent(this, username, email);
        eventPublisher.publishEvent(event);
    }

    /**
     * 用户登录
     */
    public void loginUser(String username, String loginIp) {
        System.out.println("🔐 用户登录中: " + username);
        // 执行登录逻辑...
        System.out.println("✅ 登录成功！");

        // 发布登录事件
        String loginTime = LocalDateTime.now().format(formatter);
        UserLoginEvent event = new UserLoginEvent(this, username, loginIp, loginTime);
        eventPublisher.publishEvent(event);
    }

    /**
     * 用户登出
     */
    public void logoutUser(String username) {
        System.out.println("👋 用户登出中: " + username);
        // 执行登出逻辑...
        System.out.println("✅ 登出成功！");

        // 发布登出事件
        String logoutTime = LocalDateTime.now().format(formatter);
        UserLogoutEvent event = new UserLogoutEvent(this, username, logoutTime);
        eventPublisher.publishEvent(event);
    }
}
