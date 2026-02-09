package com.zfy.mp.doc.javaTutorial.designMode.factory.AbstractFactory;

/**
 * @文件名: AbstractFactoryDemo.java
 * @工程名: bwcj-back
 * @包名: com.zfy.mp.doc.javaTutorial.designMode.factory.AbstractFactory
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-02-03 16:58
 * @版本号: V2.4.0
 */
public class AbstractFactoryDemo {
    public static void main(String[] args) {
        // 方式一：手动指定操作系统类型
        System.out.println("===== Windows 风格 =====");
        UIFactory windowsFactory = FactoryCreator.getFactory("windows");
        Client.createLoginPage(windowsFactory);

        System.out.println("\n===== MacOS 风格 =====");
        UIFactory macFactory = FactoryCreator.getFactory("macos");
        Client.createLoginPage(macFactory);

        // 方式二：自动检测当前操作系统
        System.out.println("\n===== 当前系统风格 =====");
        UIFactory autoFactory = FactoryCreator.getFactory();
        Client.createLoginPage(autoFactory);
    }
}
