package com.zfy.mp.doc.javaTutorial.designMode.factory.AbstractFactory;

/**
 * @文件名: Client.java
 * @工程名: mp
 * @包名: com.zfy.mp.doc.javaTutorial.designMode.factory.AbstractFactory
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-02-03 16:55
 * @版本号: V2.4.0
 */
public class Client {
    public static void createLoginPage(UIFactory factory)  {
        Button loginButton = factory.createButton("登录");
        Button registerButton = factory.createButton("注册");
        // 渲染所有组件
        System.out.println("========== 渲染登录页面 ==========");
        loginButton.render();
        registerButton.render();
        System.out.println("====================================\n");
    }


}
