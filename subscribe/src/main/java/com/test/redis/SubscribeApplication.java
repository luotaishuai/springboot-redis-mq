package com.test.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import javax.annotation.Resource;

/**
 * @author anonymity
 * @create 2018-12-17 17:18
 **/
@Slf4j
@SpringBootApplication
public class SubscribeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubscribeApplication.class, args);
    }

    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;

    @Bean
    public RedisMessageListenerContainer container(){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(lettuceConnectionFactory);
        container.addMessageListener(new SubscribeListener(), new PatternTopic("spring-boot-redis-mq-test-channel-1"));
        return container;
    }

    public class SubscribeListener implements MessageListener {

        @Override
        public void onMessage(Message message, byte[] bytes) {
            // 因为序列化原因，要使用new String(), 而不是String.valueOf()
            log.info("channel: {} ***---> message: {}", new String(bytes), new String(message.getBody()));
        }
    }
}
