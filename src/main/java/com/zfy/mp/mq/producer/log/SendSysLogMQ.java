package com.zfy.mp.mq.producer.log;

import com.zfy.mp.domain.entity.SysOperLog;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @文件名: SendSysLogMQ.java
 * @工程名: mp
 * @包名: com.zfy.mp.mq.producer.log
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-13 16:19
 * @版本号: V2.4.0
 */
@Component
public class SendSysLogMQ {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Value("${spring.rabbitmq.exchange.log}")
    private String exchange;
    @Value("${spring.rabbitmq.routingKey.log-system}")
    private String routingKey;

    public void saveSysLog(SysOperLog log) {
        rabbitTemplate.convertAndSend(exchange, routingKey, log);
    }
}
