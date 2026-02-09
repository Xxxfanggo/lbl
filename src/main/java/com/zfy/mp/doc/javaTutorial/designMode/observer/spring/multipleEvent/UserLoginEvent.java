package com.zfy.mp.doc.javaTutorial.designMode.observer.spring.multipleEvent;

import org.springframework.context.ApplicationEvent;

/**
 * 用户登录事件
 */
public class UserLoginEvent extends ApplicationEvent {
    private String username;
    private String loginIp;
    private String loginTime;

    public UserLoginEvent(Object source, String username, String loginIp, String loginTime) {
        super(source);
        this.username = username;
        this.loginIp = loginIp;
        this.loginTime = loginTime;
    }

    public String getUsername() { return username; }
    public String getLoginIp() { return loginIp; }
    public String getLoginTime() { return loginTime; }
}
