package com.zfy.mp.mq.consumer.broadcast;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 
 *
 * @文件名: BroadcastConsumer.java
 * @工程名: mp
 * @包名: com.zfy.mp.mq.consumer.broadcast
 * @描述: 发布订阅消费者
 * @创建人: zhongfangyu
 * @创建时间: 2025-10-21 9:40
 * @版本号: V2.4.0
 */
@Component
public class BroadcastConsumer {

    @RabbitListener(queues = "queue01")
    public void broadcastReceive01(String message) {
        System.out.println("queue01 接收到消息：" + message);
    }

    @RabbitListener(queues = "queue02")
    public void broadcastReceive02(String message) {
        System.out.println("queue02 接收到消息：" + message);
    }
}
