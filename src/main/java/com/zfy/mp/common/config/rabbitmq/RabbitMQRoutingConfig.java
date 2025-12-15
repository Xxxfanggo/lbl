package com.zfy.mp.common.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright © 2025. All rights reserved.万达信息股份有限公司
 *
 * @文件名: RabbitMQRoutingConfig.java
 * @工程名: mp
 * @包名: com.zfy.mp.config.rabbitmq
 * @描述: 路由模式
 * @创建人: zhongfangyu
 * @创建时间: 2025-10-21 9:59
 * @版本号: V2.4.0
 */
@Configuration
public class RabbitMQRoutingConfig {
    public static final String EXCHANGE = "direct_exchange";
    public static final String QUEUE01 = "queue_1";
    public static final String QUEUE02 = "queue_2";
    public static final String RoutingKey01 = "key_1";
    public static final String RoutingKey02 = "key_2";

    @Bean
    public DirectExchange directExchange_Routing() {
        return new DirectExchange(EXCHANGE);
    }
    @Bean
    public Queue queue01_Routing() {
        return new Queue(QUEUE01);
    }
    @Bean
    public Queue queue02_Routing() {
        return new Queue(QUEUE02);
    }
    @Bean
    public Binding binding01_Routing() {
        return BindingBuilder.bind(queue01_Routing()).to(directExchange_Routing()).with(RoutingKey01);
    }

    @Bean
    public Binding binding02_Routing() {
        return BindingBuilder.bind(queue02_Routing()).to(directExchange_Routing()).with(RoutingKey02);
    }
}
