package com.qf.v13emailservice.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v13.api.IEmailService;
import com.qf.v13.constant.RabbitQMConstant;
import com.qf.v13.entity.TUser;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Queue;
import java.util.UUID;

@Component
public class EmailConsumer {

    @Autowired
    private IEmailService emailService;

    @Resource(name = "redisTemplateKeySerializer")
    private RedisTemplate redisTemplate;

    @RabbitListener(queues = RabbitQMConstant.USER_EMAILSERVICE_QUEUE_REGISTER)
    @RabbitHandler
    public void handleRegister(TUser tUser){
        String email = tUser.getEmail();
        String uuid = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set("user"+uuid,tUser.getUsername());

        String activeText = "<a href='http://localhost:9095/user/userActive/"+uuid+"'>点击激活</a>";
        String subject = "账号激活";
        emailService.sendActiveMessage(email,activeText,subject);
    }
}
