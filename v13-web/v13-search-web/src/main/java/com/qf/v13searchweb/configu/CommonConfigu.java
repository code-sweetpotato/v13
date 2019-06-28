package com.qf.v13searchweb.configu;

import com.qf.v13.constant.RabbitQMConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfigu {
    //声明队列   绑定关系   声明交换机

    @Bean
    public Queue initQueue(){
        return new Queue(RabbitQMConstant.PRODUCT_SEARCH_QUEUE_ADD,true,false,false);
    }
    @Bean
    public TopicExchange initExchange(){
        return new TopicExchange(RabbitQMConstant.CENTER_EXCHANGE);
    }

    @Bean
    public Binding initBinding(Queue initQueue,TopicExchange initExchange){
        return BindingBuilder.bind(initQueue).to(initExchange).with("product.save");
    }


}
