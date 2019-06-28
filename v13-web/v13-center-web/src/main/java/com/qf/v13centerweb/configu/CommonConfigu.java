package com.qf.v13centerweb.configu;

import com.qf.v13.constant.RabbitQMConstant;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfigu {

    @Bean
    public TopicExchange createExchange(){
        return new TopicExchange(RabbitQMConstant.CENTER_EXCHANGE);
    }
}
