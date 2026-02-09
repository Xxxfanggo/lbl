package com.zfy.mp.doc.javaTutorial.designMode.observer.spring;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 日志服务监听器
 */
@Component
public class LogListener {

//    @EventListener
    public void logUserRegistration(UserRegisteredEvent event) {
        System.out.println("📝 记录注册日志: " + event.getUsername());
        System.out.println("📝 注册邮箱: " + event.getEmail());
        System.out.println("   注册时间: " + LocalDateTime.now());
    }
}
