package com.zfy.mp.doc.javaTutorial.designMode.proxy.dynamicproxy;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务实现 - 目标对象
 */
public class UserServiceImpl implements UserService {
    private Map<String, String> users = new HashMap<>();

    @Override
    public void addUser(String username, String email) {
        users.put(username, email);
        System.out.println("✅ 用户已添加: " + username + " <" + email + ">");
    }

    @Override
    public void deleteUser(String username) {
        users.remove(username);
        System.out.println("🗑️  用户已删除: " + username);
    }

    @Override
    public String getUser(String username) {
        String email = users.get(username);
        System.out.println("🔍 查询用户: " + username + " -> " + email);
        return email;
    }
}
