package com.qf.v13itemweb.configu;


import com.qf.v13.constant.RabbitQMConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class CommonConfigu {

    //声明队列和交换机对象   声明绑定关系
    @Bean
    public Queue initQueue(){
        return new Queue(RabbitQMConstant.PRODUCT_ITEM_QUEUE_ADD,true,false,false);
    }

    @Bean
    public TopicExchange initExchange(){
        return new TopicExchange(RabbitQMConstant.CENTER_EXCHANGE);
    }

    @Bean
    public Binding initBinding(Queue initQueue, TopicExchange exchange){
        return BindingBuilder.bind(initQueue).to(exchange).with("product.save");


    }



    @Bean
    public ThreadPoolExecutor getPool(){
        int cpus = Runtime.getRuntime().availableProcessors();

        //ExecutorService executorService = Executors.newSingleThreadExecutor();
        //生成线程池
        return new ThreadPoolExecutor(cpus,cpus*2,10, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>(100));


    }
}
