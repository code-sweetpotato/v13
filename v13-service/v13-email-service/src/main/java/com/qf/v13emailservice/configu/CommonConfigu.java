package com.qf.v13emailservice.configu;

import com.qf.v13.constant.RabbitQMConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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

    @Bean
    public Queue initQueue(){
        return new Queue(RabbitQMConstant.USER_EMAILSERVICE_QUEUE_REGISTER);
    }

    @Bean
    public Binding binding(TopicExchange exchange,Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(RabbitQMConstant.USER_ROUTING_REGISTER);
    }

    @Bean(name = "redisTemplateKeySerializer")
    public RedisTemplate<String,Object> setKeySerizer(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;

    }
}
