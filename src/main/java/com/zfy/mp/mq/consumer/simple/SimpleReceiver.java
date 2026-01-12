package com.zfy.mp.mq.consumer.simple;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static java.lang.Thread.sleep;

/**
 * 
 *
 * @文件名: SimpleReceiver.java
 * @工程名: mp
 * @包名: com.zfy.mp.mq.consumer.simple
 * @描述: 简单模式消费者
 * @创建人: zhongfangyu
 * @创建时间: 2025-10-17 16:58
 * @版本号: V2.4.0
 */
@Component
public class SimpleReceiver {
    @RabbitListener(queues = "simple.hello")
    public void receive(String message) throws InterruptedException {
        sleep(10000);
        System.out.println("简单模式消费者：" + message);
    }
}
