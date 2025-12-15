package com.zfy.mp.mq.consumer.topic;

import com.zfy.mp.common.config.rabbitmq.RabbitMQTopicConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 
 *
 * @文件名: topicConsumer.java
 * @工程名: mp
 * @包名: com.zfy.mp.mq.consumer.topic
 * @描述: 主题消费者
 * @创建人: zhongfangyu
 * @创建时间: 2025-10-21 14:12
 * @版本号: V2.4.0
 */
@Component
public class topicConsumer {
    @RabbitListener(queues = RabbitMQTopicConfig.QUEUE01)
    public void receive01(String message) {
        System.out.println("queue_topic01 接收到消息：" + message);
    }

    @RabbitListener(queues = RabbitMQTopicConfig.QUEUE02)
    public void receive02(String message) {
        System.out.println("queue_topic02 接收到消息：" + message);
    }

    @RabbitListener(queues = RabbitMQTopicConfig.QUEUE03)
    public void receive03(String message) {
        System.out.println("queue_topic03 接收到消息：" + message);
    }
}
