package com.zfy.mp.common.config.rabbitmq;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 *
 * @文件名: LogRabbitConfig.java
 * @工程名: mp
 * @包名: com.zfy.mp.common.config.rabbitmq
 * @描述: 日志队列
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-13 16:10
 * @版本号: V2.4.0
 */
public class LogRabbitConfig {
    @Value("${spring.rabbitmq.queue.log-system}")
    private String LOG_SYSTEM_QUEUE;
    @Value("${spring.rabbitmq.queue.log-login}")
    private String LOG_LOGIN_QUEUE;

    @Value("${spring.rabbitmq.exchange.log}")
    private String LOG_EXCHANGE;

    @Value("${spring.rabbitmq.routingKey.log-system}")
    private String LOG_SYSTEM_ROUTING_KEY;
    @Value("${spring.rabbitmq.routingKey.log-login}")
    private String LOG_LOGIN_ROUTING_KEY;

    /**
     * 1. 创建队列 2. 创建交换机 3. 绑定队列和交换机
     */
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(LOG_EXCHANGE);
    }
    // -------------系统日志队列------------------
    @Bean
    public Queue systemQueue() {
        return new Queue(LOG_SYSTEM_QUEUE);
    }
    @Bean
    public void bindSystemLogQueue() {
        BindingBuilder.bind(systemQueue()).to(exchange()).with(LOG_SYSTEM_ROUTING_KEY);
    }
    // -------------登录日志队列------------------
    @Bean
    public Queue loginQueue() {
        return new Queue(LOG_LOGIN_QUEUE);
    }
    @Bean
    public void bindLoginLogQueue() {
        BindingBuilder.bind(loginQueue()).to(exchange()).with(LOG_LOGIN_ROUTING_KEY);
    }
}
