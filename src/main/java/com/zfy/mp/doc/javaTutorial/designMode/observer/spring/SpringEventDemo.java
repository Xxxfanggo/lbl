package com.zfy.mp.doc.javaTutorial.designMode.observer.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Spring事件机制演示
 */
@SpringBootApplication
public class SpringEventDemo {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringEventDemo.class, args);

        UserService userService = context.getBean(UserService.class);

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("      用户注册 - 观察者模式");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        userService.registerUser("张三", "zhangsan@example.com");

        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("      所有监听器已自动处理");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        context.close();
    }
}
