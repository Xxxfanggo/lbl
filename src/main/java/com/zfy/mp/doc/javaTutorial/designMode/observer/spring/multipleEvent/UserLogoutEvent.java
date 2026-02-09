package com.zfy.mp.doc.javaTutorial.designMode.observer.spring.multipleEvent;

import org.springframework.context.ApplicationEvent;

/**
 * 用户登出事件
 */
public class UserLogoutEvent extends ApplicationEvent {
    private String username;
    private String logoutTime;

    public UserLogoutEvent(Object source, String username, String logoutTime) {
        super(source);
        this.username = username;
        this.logoutTime = logoutTime;
    }

    public String getUsername() { return username; }
    public String getLogoutTime() { return logoutTime; }
}
