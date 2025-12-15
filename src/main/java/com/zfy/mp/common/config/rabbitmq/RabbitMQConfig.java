package com.zfy.mp.common.config.rabbitmq;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * 
 *
 * @文件名: RabbitMQConfig.java
 * @工程名: mp
 * @包名: com.zfy.mp.config.rabbitmq
 * @描述: MQ简单模式配置类
 * @创建人: zhongfangyu
 * @创建时间: 2025-10-17 16:44
 * @版本号: V2.4.0
 */
@Configuration
public class RabbitMQConfig {
    @Resource
    private RabbitTemplate rabbitTemplate;

    private static Logger logger = LogManager.getLogger(RabbitMQConfig.class.getName());


    @PostConstruct
    public void init() {
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                logger.error("触发return callback,");
                logger.debug("exchange: {}", returned.getExchange());
                logger.debug("routingKey: {}", returned.getRoutingKey());
                logger.debug("message: {}", returned.getMessage());
                logger.debug("replyCode: {}", returned.getReplyCode());
                logger.debug("replyText: {}", returned.getReplyText());
            }
        });
    }

    /**
     * 创建队列
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue("simple.hello");
    }
}
