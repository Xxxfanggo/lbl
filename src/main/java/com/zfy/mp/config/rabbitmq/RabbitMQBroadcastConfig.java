package com.zfy.mp.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright © 2025. All rights reserved.万达信息股份有限公司
 *
 * @文件名: RabbitMQBroadcastConfig.java
 * @工程名: mp
 * @包名: com.zfy.mp.config.rabbitmq
 * @描述: 广播模式
 * @创建人: zhongfangyu
 * @创建时间: 2025-10-21 9:24
 * @版本号: V2.4.0
 */
@Configuration
public class RabbitMQBroadcastConfig {
    public static final String EXCHANGE = "direct_exchange";
    public static final String QUEUE01 = "queue01";
    public static final String QUEUE02 = "queue02";
    public static final String RoutingKey01 = "queue_route01";
    public static final String RoutingKey02 = "queue_route02";

    @Bean
    public Queue queue01() {
        return new Queue(QUEUE01);
    }
    @Bean
    public Queue queue02() {
        return new Queue(QUEUE02);
    }
    @Bean
    public DirectExchange  directExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding broadcastBinding01() {
        return BindingBuilder.bind(queue01()).to(directExchange()).with(RoutingKey01);
    }
    @Bean
    public Binding broadcastBinding02() {
        return BindingBuilder.bind(queue02()).to(directExchange()).with(RoutingKey02);
    }
}
