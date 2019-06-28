package com.qf.v13productservice.config;


import com.github.pagehelper.PageHelper;
import com.qf.v13.entity.TProductType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Properties;

@Configuration
public class CommonConfig {
    @Bean
    public PageHelper getPageHelper(){
        PageHelper pageHelper = new PageHelper();
        //
        Properties properties = new Properties();
        properties.setProperty("dialect","mysql");
        properties.setProperty("reasonable","true");
        //
        pageHelper.setProperties(properties);
        return pageHelper;
    }

    @Bean
    public RedisTemplate<String,List<TProductType>> initRedisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String,List<TProductType>> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
}
