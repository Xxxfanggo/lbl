package com.zfy.mp.doc.javaTutorial.designMode.observer.spring;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 积分服务监听器
 */
@Component
public class PointsListener {

//    @EventListener
    public void awardRegistrationPoints(UserRegisteredEvent event) {
        System.out.println("🎁 为用户 " + event.getUsername() + " 奖励 100 积分");
    }
}
