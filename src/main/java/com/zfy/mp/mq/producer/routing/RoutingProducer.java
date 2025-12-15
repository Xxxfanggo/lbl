package com.zfy.mp.mq.producer.routing;

import com.zfy.mp.config.rabbitmq.RabbitMQRoutingConfig;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Copyright © 2025. All rights reserved.万达信息股份有限公司
 *
 * @文件名: routing.java
 * @工程名: mp
 * @包名: com.zfy.mp.mq.producer
 * @描述: 路由模式
 * @创建人: zhongfangyu
 * @创建时间: 2025-10-21 10:06
 * @版本号: V2.4.0
 */
@Component
public class RoutingProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;
    public void send(String message, String routingKey) {
        System.out.println("发送消息：" + message);
        rabbitTemplate.convertAndSend(RabbitMQRoutingConfig.EXCHANGE, routingKey, message);
    }
}
