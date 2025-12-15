package com.zfy.mp.common.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright © 2025. All rights reserved.万达信息股份有限公司
 *
 * @文件名: RabbitTopicConfig.java
 * @工程名: mp
 * @包名: com.zfy.mp.config.rabbitmq
 * @描述: 主题模式
 * @创建人: zhongfangyu
 * @创建时间: 2025-10-21 14:03
 * @版本号: V2.4.0
 */
@Configuration
public class RabbitMQTopicConfig {
    public static final String EXCHANGE_NAME = "topic_exchange";
    public static final String QUEUE01 = "queue_topic01";
    public static final String QUEUE02 = "queue_topic02";
    public static final String QUEUE03 = "queue_topic03";
    public static final String RoutingKey01 = "key_1.*";
    public static final String RoutingKey02 = "key_2.*";
    public static final String RoutingKey03 = "*.key_3";

    @Bean
    public TopicExchange topicExchange_Topic() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queue01_Topic() {
        return new Queue(QUEUE01);
    }
    @Bean
    public Queue queue02_Topic() {
        return new Queue(QUEUE02);
    }
    @Bean
    public Queue queue03_Topic() {
        return new Queue(QUEUE03);
    }

    @Bean
    public Binding binding01_Topic() {
        return BindingBuilder.bind(queue01_Topic()).to(topicExchange_Topic()).with(RoutingKey01);
    }
    @Bean
    public Binding binding02_Topic() {
        return BindingBuilder.bind(queue02_Topic()).to(topicExchange_Topic()).with(RoutingKey02);
    }
    @Bean
    public Binding binding03_Topic() {
        return BindingBuilder.bind(queue03_Topic()).to(topicExchange_Topic()).with(RoutingKey03);
    }
}
