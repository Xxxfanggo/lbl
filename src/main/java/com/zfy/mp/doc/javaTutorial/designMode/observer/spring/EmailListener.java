package com.zfy.mp.doc.javaTutorial.designMode.observer.spring;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 邮件服务监听器
 */
@Component
public class EmailListener {

    @EventListener
    public void handleUserRegistration(UserRegisteredEvent event) {
        System.out.println("📧 发送欢迎邮件给: " + event.getEmail());
        System.out.println("   邮件内容: 亲爱的 " + event.getUsername() + "，欢迎加入我们！");
    }
}
