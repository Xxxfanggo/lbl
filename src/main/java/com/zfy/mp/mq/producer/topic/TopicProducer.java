package com.zfy.mp.mq.producer.topic;

import com.zfy.mp.common.config.rabbitmq.RabbitMQTopicConfig;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 
 *
 * @文件名: topicProducer.java
 * @工程名: mp
 * @包名: com.zfy.mp.mq.producer.topic
 * @描述: 主题
 * @创建人: zhongfangyu
 * @创建时间: 2025-10-21 14:07
 * @版本号: V2.4.0
 */
@Component
public class TopicProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(String message, String routingKey) {
        System.out.println("key: "+routingKey + "发送消息：" + message);
        rabbitTemplate.convertAndSend(RabbitMQTopicConfig.EXCHANGE_NAME, routingKey, message);
    }

//    private void send2key2(String message, String routingKey) {
//        System.out.println("key: "+routingKey + "发送消息：" + message);
//
//        rabbitTemplate.convertAndSend(RabbitMQTopicConfig.EXCHANGE_NAME, routingKey, message);
//    }
//
//    private void send2key3(String message, String routingKey) {
//        System.out.println("发送消息：" + message);
//        rabbitTemplate.convertAndSend(RabbitMQTopicConfig.EXCHANGE_NAME, routingKey, message);
//    }
}
