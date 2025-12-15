package com.zfy.mp.mq.producer.broadcast;

import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 
 *
 * @文件名: BroadcastProducer.java
 * @工程名: mp
 * @包名: com.zfy.mp.mq.producer.broadcast
 * @描述: 广播模式生产者
 * @创建人: zhongfangyu
 * @创建时间: 2025-10-21 9:38
 * @版本号: V2.4.0
 */
@Component
public class BroadcastProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;
    public void send(String message) {
        System.out.println("发送消息：" + message);
        rabbitTemplate.convertAndSend("direct_exchange","queue_route01", message);
    }
}
