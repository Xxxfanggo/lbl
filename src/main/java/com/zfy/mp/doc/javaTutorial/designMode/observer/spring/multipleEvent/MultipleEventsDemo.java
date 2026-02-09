package com.zfy.mp.doc.javaTutorial.designMode.observer.spring.multipleEvent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 多事件演示
 * 展示如何区分和处理不同的事件
 */
@SpringBootApplication
@EnableAsync  // 启用异步支持
public class MultipleEventsDemo {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(MultipleEventsDemo.class, args);

        EnhancedUserService userService = context.getBean(EnhancedUserService.class);

        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║          Spring多事件处理 - 观察者模式演示                 ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝\n");

        // 演示1：普通用户注册
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("                  场景1：普通用户注册");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        userService.registerUser("张三", "zhangsan@example.com");

        Thread.sleep(500); // 等待异步处理

        // 演示2：管理员注册
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("                  场景2：管理员注册");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        userService.registerUser("admin", "admin@example.com");

        Thread.sleep(500);

        // 演示3：用户登录
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("                  场景3：用户登录");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        userService.loginUser("张三", "192.168.1.100");

        Thread.sleep(500);

        // 演示4：管理员登录
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("                  场景4：管理员登录");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        userService.loginUser("admin", "192.168.1.1");

        Thread.sleep(500);

        // 演示5：用户登出
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("                  场景5：用户登出");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        userService.logoutUser("张三");

        Thread.sleep(500);

        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("                  演示完成");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        context.close();
    }
}
