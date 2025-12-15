package com.zfy.mp.mq.producer.simple;

import jakarta.annotation.Resource;
import org.springframework.amqp.core.Correlation;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 
 *
 * @文件名: SimpleProducer.java
 * @工程名: mp
 * @包名: com.zfy.mp.mq.producer.simple
 * @描述: 简单模式生产者
 * @创建人: zhongfangyu
 * @创建时间: 2025-10-17 16:59
 * @版本号: V2.4.0
 */
@Component
public class SimpleProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;
    public void send(String message) {
        System.out.println("发送祝福：" + message);
        rabbitTemplate.convertAndSend("simple.hello", message);

    }

    public void send_confirm(String message, String routingKey) throws InterruptedException{
        // 1. 创建
        CorrelationData cd = new CorrelationData();
        cd.getFuture().whenComplete((correlation, throwable) -> {
            if (correlation.isAck()) {
                System.out.println("发送成功：" + correlation);
            } else {
                System.out.println("发送失败：" + throwable);
            }
//            if (throwable != null) {
//                System.out.println("发送失败：" + throwable);
//            } else {
//                System.out.println("发送成功：" + correlation);
//            }
        });
        System.out.println("发送确认：" + message);
// 3.发送消息
        rabbitTemplate.convertAndSend("amq.direct", routingKey, "hello", cd);
        Thread.sleep(2000);
    }
}
