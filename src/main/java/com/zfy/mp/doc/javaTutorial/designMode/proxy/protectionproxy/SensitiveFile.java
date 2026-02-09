package com.zfy.mp.doc.javaTutorial.designMode.proxy.protectionproxy;

/**
 * 敏感文件接口 - 抽象主题
 */
public interface SensitiveFile {
    void read(User user);
    void write(User user, String content);
    void delete(User user);
}
