package com.zfy.mp.service.publIc.impl;

import com.zfy.mp.common.constants.RedisConst;
import com.zfy.mp.common.utils.RedisCache;
import com.zfy.mp.mq.producer.email.SendEmailMQ;
import com.zfy.mp.service.publIc.PublicService;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 *
 * @文件名: PublicServiceImpl.java
 * @工程名: mp
 * @包名: com.zfy.mp.service.publIc
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-08 15:55
 * @版本号: V2.4.0
 */
@Service
public class PublicServiceImpl implements PublicService {

    @Resource
    private RedisCache redisCache;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private SendEmailMQ sendEmailMQ;
    @Override
    public String registerEmailVerifyCode(String email, String type) {
//        String verifyCode = String.valueOf(((Math.random() * 9 + 1) * 100000));
        String verifyCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        // 存到redis，设置过期时间为5分钟
        redisCache.setCacheObject(RedisConst.VERIFY_CODE + type + RedisConst.SEPARATOR + email, verifyCode, RedisConst.VERIFY_CODE_EXPIRATION, TimeUnit.MINUTES);
        sendEmailMQ.sendEmailVerifyCode(email, verifyCode, type);
        return verifyCode;
    }
}
