package com.zfy.mp.doc.javaTutorial.designMode.observer.spring.multipleEvent;

import com.zfy.mp.doc.javaTutorial.designMode.observer.spring.UserRegisteredEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 通用事件监听器
 * 演示如何区分和处理不同的事件
 */
@Component
public class UniversalEventListener {

    /**
     * 方法1：通过方法参数类型自动匹配事件
     * Spring会根据参数类型自动调用对应的方法
     */
    @EventListener
    public void handleUserRegistration(UserRegisteredEvent event) {
        System.out.println("📧 【注册事件】用户 " + event.getUsername() + " 注册成功");
        System.out.println("   邮箱: " + event.getEmail());
    }

    @EventListener
    public void handleUserLogin(UserLoginEvent event) {
        System.out.println("🔐 【登录事件】用户 " + event.getUsername() + " 登录成功");
        System.out.println("   登录IP: " + event.getLoginIp());
        System.out.println("   登录时间: " + event.getLoginTime());
    }

    @EventListener
    public void handleUserLogout(UserLogoutEvent event) {
        System.out.println("👋 【登出事件】用户 " + event.getUsername() + " 已登出");
        System.out.println("   登出时间: " + event.getLogoutTime());
    }

    /**
     * 方法2：使用条件表达式过滤事件
     * 只有满足条件的事件才会被处理
     * 注意：条件表达式要指定具体的事件类型，避免匹配到其他事件
     */
    @EventListener(condition = "#event.username == 'admin'")
    public void handleAdminRegistration(UserRegisteredEvent event) {
        System.out.println("⚠️ 【管理员关注】管理员账户注册: " + event.getUsername());
    }

    @EventListener(condition = "#event.username == 'admin'")
    public void handleAdminLogin(UserLoginEvent event) {
        System.out.println("⚠️ 【管理员关注】管理员登录: " + event.getUsername());
    }

    /**
     * 方法3：监听所有事件（不推荐，仅用于演示）
     * 可以通过instanceof判断事件类型
     */
    @EventListener
    public void handleAllEvents(ApplicationEvent event) {
        // 只处理我们自定义的事件
        if (event instanceof UserRegisteredEvent || 
            event instanceof UserLoginEvent || 
            event instanceof UserLogoutEvent) {
            System.out.println("📝 【通用日志】事件类型: " + event.getClass().getSimpleName());
        }
    }

    /**
     * 方法4：异步处理事件
     * 使用@Async注解可以让事件处理异步执行
     */
    @Async
    @EventListener
    public void asyncHandleEvent(ApplicationEvent event) {
        if (event instanceof UserRegisteredEvent) {
            UserRegisteredEvent ure = (UserRegisteredEvent) event;
            System.out.println("🔄 【异步处理】正在为用户 " + ure.getUsername() + " 初始化数据...");
            try {
                Thread.sleep(1000); // 模拟耗时操作
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("✅ 【异步处理】用户 " + ure.getUsername() + " 数据初始化完成");
        }
    }
}
