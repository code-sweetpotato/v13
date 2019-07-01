package com.qf.v13userservice.configu;

import com.qf.v13.constant.RabbitQMConstant;
import com.qf.v13.entity.TUser;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class CommonConfigu extends WebSecurityConfigurerAdapter{

    //声明队列  交换机   绑定关系
    @Bean
    public TopicExchange initExchange(){
        return new TopicExchange(RabbitQMConstant.CENTER_EXCHANGE);
    }
    @Bean
    public Queue initQueue(){
        return new Queue(RabbitQMConstant.USER_USERSERVICE_QUEUE_REGISTER);
    }

    @Bean
    public Binding binding(TopicExchange exchange,Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(RabbitQMConstant.USER_ROUTING_REGISTER);
    }

    @Bean(name = "redis")
    public RedisTemplate<String,TUser> initRedis(RedisConnectionFactory factory){
        RedisTemplate<String,TUser> redisTemplate = new RedisTemplate();

        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
