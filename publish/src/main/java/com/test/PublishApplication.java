package com.test;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * 将消息发布到指定通道
 * @author anonymity
 * @create 2018-12-17 17:14
 **/
@Slf4j
@EnableScheduling
@SpringBootApplication
public class PublishApplication {
    public static void main(String[] args) {
        SpringApplication.run(PublishApplication.class, args);
    }

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void publish(String channel, Object jsonMsg){
        stringRedisTemplate.convertAndSend(channel, jsonMsg);
    }

    @Scheduled(fixedDelay = 1000 * 10)
    public void test(){
        try {
            publish("spring-boot-redis-mq-test-channel-1", JSON.toJSONString("hello,i'm redis mq message!"));
        }catch (Exception e){
            log.error("________error_________");
        }
    }
}
