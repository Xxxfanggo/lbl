package com.zfy.mp.common.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 
 *
 * @文件名: RabbitMQConfig.java
 * @工程名: mp
 * @包名: com.zfy.mp.config.rabbitmq
 * @描述: MQ工作列队模式配置类
 * @创建人: zhongfangyu
 * @创建时间: 2025-10-17 16:44
 * @版本号: V2.4.0
 */
@Configuration
public class RabbitMQQueueConfig {

    // 队列1
   public static final String QUEUE1 = "queue01";
   // 队列2
   public static final String QUEUE2 = "queue02";
   // 交换机
    public static final String EXCHANGE_NAME = "fanout_exchange";

    @Bean
    public Queue queue1() {
        return new Queue(QUEUE1);
    }

    @Bean
    public Queue queue2() {
        return new Queue(QUEUE2);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }
    @Bean
    public Binding binding01() {
        return BindingBuilder.bind(queue1()).to(fanoutExchange());
    }

    @Bean
    public Binding binding02() {
        return BindingBuilder.bind(queue2()).to(fanoutExchange());
    }
}
