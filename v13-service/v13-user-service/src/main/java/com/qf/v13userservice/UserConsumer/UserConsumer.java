package com.qf.v13userservice.UserConsumer;


import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v13.api.IUserService;
import com.qf.v13.constant.RabbitQMConstant;
import com.qf.v13.entity.TUser;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {

    @Autowired
    private IUserService userService;

    @RabbitListener(queues = RabbitQMConstant.USER_USERSERVICE_QUEUE_REGISTER)
    @RabbitHandler
    public void handleegister(TUser tUser){

        System.out.println(userService.insertSelective(tUser));
    }
}
