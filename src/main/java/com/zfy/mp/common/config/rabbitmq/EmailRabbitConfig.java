package com.zfy.mp.common.config.rabbitmq;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 *
 * @文件名: EmailRabbitConfig.java
 * @工程名: mp
 * @包名: com.zfy.mp.common.config.rabbit
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-12 14:18
 * @版本号: V2.4.0
 */
public class EmailRabbitConfig {

    @Value("${spring.rabbitmq.queue.email}")
    private String MAIL_QUEUE;
    @Value("${spring.rabbitmq.exchange.email}")
    private String MAIL_EXCHANGE;
    @Value("${spring.rabbitmq.routingKey.email}")
    private String MAIL_ROUTING_KEY;

    /**
     * 创建邮件队列
     * @return
     */
    @Bean
    public Queue emailQueue() {
        return new Queue(MAIL_QUEUE);
    }
    /**
     * 创建邮件直连交换机
     * @return
     */
    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange(MAIL_EXCHANGE, true, false);
    }
    /**
     * 绑定邮件队列到邮件交换机
     * @return
     */
    @Bean
    public void bindEmailQueue() {
        BindingBuilder.bind(emailQueue()).to(emailExchange()).with(MAIL_ROUTING_KEY);
    }


}
