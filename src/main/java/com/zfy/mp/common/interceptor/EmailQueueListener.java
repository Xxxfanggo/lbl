package com.zfy.mp.common.interceptor;

import com.zfy.mp.common.constants.RabbitConst;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * @文件名: EmailQueueListener.java
 * @工程名: mp
 * @包名: com.zfy.mp.common.interceptor
 * @描述: 邮件队列监听器
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-12 17:07
 * @版本号: V2.4.0
 */
@Log4j2
@Component
public class EmailQueueListener {
    @RabbitListener(queues = RabbitConst.MAIL_QUEUE)
    public void receive(Map<String, Object> data) {
        log.info("接收到邮件队列消息：{}", data);
    }
}
