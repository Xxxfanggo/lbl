package com.zfy.mp;

import com.zfy.mp.config.rabbitmq.RabbitMQRoutingConfig;
import com.zfy.mp.mq.producer.routing.RoutingProducer;
import com.zfy.mp.mq.producer.broadcast.BroadcastProducer;
import com.zfy.mp.mq.producer.queue.QueueProducer;
import com.zfy.mp.mq.producer.simple.SimpleProducer;
import com.zfy.mp.mq.producer.topic.TopicProducer;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class MpApplicationTests {


    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private SimpleProducer simpleProducer;

    @Resource
    private QueueProducer queueProducer;

    @Resource
    private BroadcastProducer broadcastProducer;

    @Resource
    private RoutingProducer routingProducer;

    @Resource
    private TopicProducer topicProducer;


    @Test
    void testString() {
        redisTemplate.opsForValue().set("name", "zfy");
        Object name = redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }

//    @Test
//    void  testSaveUser()  {
//        Map<String , Object> user = new HashMap<>();
//        user.put("id", 100);
//        user.put("name", "zfy");
//        redisTemplate.opsForValue().set("user:100", user);
//        Object o = redisTemplate.opsForValue().get("user:100");
//        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
//        stringRedisTemplate.watch("user:100");
//        stringRedisTemplate.multi();
//        // todo
//        stringRedisTemplate.exec();
//
//        System.out.println(o);
//    }

    @Test
    void testRabbitMQ_Simple() {
        simpleProducer.send("hello world");
    }

    @Test
    void testRabbitMQ_Queue() {
        queueProducer.send("队列发送消息");
    }

    @Test
    void testRabbitMQ_Broadcast() {
        broadcastProducer.send("广播发送消息");
    }

    @Test
    void testRabbitMQ_Routing() {
        routingProducer.send("路由模式发送消息, queue01", RabbitMQRoutingConfig.RoutingKey01);
        routingProducer.send("路由模式发送消息, queue02", RabbitMQRoutingConfig.RoutingKey02);
    }

    @Test
    void testRabbitMQ_Topic() {
        topicProducer.send("主题模式发送消息", "key_1.123");
        topicProducer.send("主题模式发送消息", "key_2.123");
        topicProducer.send("主题模式发送消息", "123.key_3");
//        topicProducer.send("主题模式发送消息, queue02", RabbitMQRoutingConfig.RoutingKey02);
//        topicProducer.send("主题模式发送消息, queue03", RabbitMQRoutingConfig.RoutingKey03);
    }

    @Test
    void testRetrun() throws InterruptedException {
        simpleProducer.send_confirm("hello world", "1");
    }

}
