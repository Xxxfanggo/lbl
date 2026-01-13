package com.zfy.mp.common.interceptor;

import com.rabbitmq.client.Channel;
import com.zfy.mp.common.constants.RabbitConst;
import com.zfy.mp.domain.entity.SysOperLog;
import com.zfy.mp.mapper.LogMapper;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *
 * @文件名: SysLogQueueListener.java
 * @工程名: mp
 * @包名: com.zfy.mp.common.interceptor
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-13 16:23
 * @版本号: V2.4.0
 */
@Log4j2
@Component
public class SysLogQueueListener {
    @Resource
    private LogMapper logMapper;

    @RabbitListener(queues = RabbitConst.LOG_SYSTEM_QUEUE, concurrency = "5-10")
    public void handlerSystemLog(SysOperLog logEntity){
        try {
            log.info("--------------消费系统操作日志--------------");
            if (logMapper.insert(logEntity) > 0) {
//                channel.basicAck(tag, false);
                log.info("系统操作日志插入数据库成功，ID: {}", logEntity.getId());
            }
        } catch (Exception e) {
            log.error("处理系统操作日志时发生异常: {}", e);
//            channel.basicNack(tag, false, false);  // 不重新入队
        }
    }
}
