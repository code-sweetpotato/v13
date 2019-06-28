package com.qf.v13registerweb.configu;

import com.qf.v13.constant.RabbitQMConstant;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class CommonConfigu {

    @Bean
    public TopicExchange initExchange(){
        return new TopicExchange(RabbitQMConstant.CENTER_EXCHANGE);
    }

    @Bean(name = "redisTemplateKeySerializer")
    public RedisTemplate<String,Object> setKeySerizer(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;

    }
}
