package com.zfy.mp.mq.consumer.routing;

import com.zfy.mp.common.config.rabbitmq.RabbitMQRoutingConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Copyright © 2025. All rights reserved.万达信息股份有限公司
 *
 * @文件名: routing.java
 * @工程名: mp
 * @包名: com.zfy.mp.mq.consumer
 * @描述: 路由模式消费者
 * @创建人: zhongfangyu
 * @创建时间: 2025-10-21 10:08
 * @版本号: V2.4.0
 */
@Component
public class RoutingReceiver {
    @RabbitListener(queues = RabbitMQRoutingConfig.QUEUE01)
    public void routingReceive01(String message) {
        System.out.println("routingReceive01 接收到消息：" + message);
    }
    @RabbitListener(queues = RabbitMQRoutingConfig.QUEUE02)
    public void routingReceive02(String message) {
        System.out.println("routingReceive02 接收到消息：" + message);
    }
}
