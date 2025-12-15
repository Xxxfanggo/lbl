package com.zfy.mp.mq.consumer.queue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Copyright © 2025. All rights reserved.万达信息股份有限公司
 *
 * @文件名: QueueReceiver.java
 * @工程名: mp
 * @包名: com.zfy.mp.mq.consumer.simple
 * @描述: 队列模式消费者
 * @创建人: zhongfangyu
 * @创建时间: 2025-10-20 15:10
 * @版本号: V2.4.0
 */
@Component
public class QueueReceiver {
    @RabbitListener(queues = "queue01")
    public void receive01(String message) {
        System.out.println("queue01 接收到消息：" + message);
    }

    @RabbitListener(queues = "queue02")
    public void receive02(String message) {
        System.out.println("queue02 接收到消息：" + message);
    }
}
