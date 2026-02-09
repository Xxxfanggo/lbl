package com.zfy.mp.doc.javaTutorial.designMode.proxy.dynamicproxy;

/**
 * 用户服务接口
 */
public interface UserService {
    void addUser(String username, String email);
    void deleteUser(String username);
    String getUser(String username);
}
