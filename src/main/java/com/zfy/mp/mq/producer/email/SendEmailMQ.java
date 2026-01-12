package com.zfy.mp.mq.producer.email;

import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @文件名: SendEmail.java
 * @工程名: mp
 * @包名: com.zfy.mp.mq.producer.email
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-12 17:21
 * @版本号: V2.4.0
 */
@Component
public class SendEmailMQ {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Value("${spring.rabbitmq.exchange.email}")
    private String exchange;
    @Value("${spring.rabbitmq.routingKey.email}")
    private String routingKey;

    public void sendEmailVerifyCode(String email, String code, String type) {
        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("code", code);
        data.put("type", type);
        rabbitTemplate.convertAndSend(exchange, routingKey, data);
    }
}
