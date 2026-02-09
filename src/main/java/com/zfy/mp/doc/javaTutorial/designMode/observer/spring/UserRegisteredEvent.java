package com.zfy.mp.doc.javaTutorial.designMode.observer.spring;

import org.springframework.context.ApplicationEvent;

/**
 * 用户注册事件
 */
public class UserRegisteredEvent extends ApplicationEvent {
    private String username;
    private String email;

    public UserRegisteredEvent(Object source, String username, String email) {
        super(source);
        this.username = username;
        this.email = email;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
}
