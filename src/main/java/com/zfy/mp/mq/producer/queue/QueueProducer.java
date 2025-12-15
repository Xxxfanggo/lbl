package com.zfy.mp.mq.producer.queue;

import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Copyright © 2025. All rights reserved.万达信息股份有限公司
 *
 * @文件名: QueueProducer.java
 * @工程名: mp
 * @包名: com.zfy.mp.mq.producer.queue
 * @描述: 队列模式生产者
 * @创建人: zhongfangyu
 * @创建时间: 2025-10-20 15:08
 * @版本号: V2.4.0
 */
@Component
public class QueueProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(String message) {
        System.out.println("发送消息：" + message);
        rabbitTemplate.convertAndSend("fanout_exchange","", message);
    }
}
