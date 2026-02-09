package com.zfy.mp.doc.javaTutorial.designMode.proxy.protectionproxy;

/**
 * 用户类
 */
public class User {
    private String username;
    private Permission permission;

    public User(String username, Permission permission) {
        this.username = username;
        this.permission = permission;
    }

    public String getUsername() { return username; }
    public Permission getPermission() { return permission; }
}
