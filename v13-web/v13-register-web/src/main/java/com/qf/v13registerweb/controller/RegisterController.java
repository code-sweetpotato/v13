package com.qf.v13registerweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v13.api.IEmailService;
import com.qf.v13.api.IUserService;
import com.qf.v13.constant.RabbitQMConstant;
import com.qf.v13.entity.TUser;
import com.qf.v13.pojo.ResultBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class RegisterController {

    @Reference
    private IUserService userService;

    @Reference
    private IEmailService emailService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Resource(name = "redisTemplateKeySerializer")
    private RedisTemplate redisTemplate;


    @RequestMapping("tologin")
    public String testController(){

        return "login";
    }

    @RequestMapping("register")
    public String register(){

        return "register";
    }

    @RequestMapping("existUser")
    @ResponseBody
    public ResultBean isExistUser( String username){
        int result = userService.isExistByUsername(username);

        if(result>0){
            return new ResultBean<String>("500","用户名已经存在!");

        } else {
            return new ResultBean("200","用户名可以使用!");
        }

    }

    @RequestMapping("getCode")
    @ResponseBody
    //给指定的邮箱发送验证码,方法参数为邮箱或者手机号
    public ResultBean<String> getCode(String sendType, HttpSession session){
        if(sendType.contains("@")){
            String code = emailService.sendCode(sendType);
            session.setAttribute("volicateCode",code);
            return new  ResultBean("200",code);
        }else {
            return new ResultBean<>("500","暂不支持手机接收验证码功能");
        }


    }


    @RequestMapping("isExistEmail")
    @ResponseBody
    //检查注册的邮箱是否唯一
    public ResultBean<String> isExistEmail(String codeType){

        int result = userService.isExistEmail(codeType);
        if(result>0){
            return new ResultBean<>("500","对不起!该邮箱已注册账号");
        }else {
            return new ResultBean<>("200","验证码已发送至邮箱");
        }

    }

    @RequestMapping("toregister")
    @ResponseBody
    //储存用户信息和发送激活连接
    public ResultBean<String> toregister(TUser tUser){

        //String password,String username,String email
/*        TUser tuser = new TUser();
        tuser.setEmail(email);
        tuser.setPassword(password);
        tuser.setUsername(username);*/

        rabbitTemplate.convertAndSend(RabbitQMConstant.CENTER_EXCHANGE,RabbitQMConstant.USER_ROUTING_REGISTER,tUser);
        return new ResultBean<>("200","注册成功!请激活账号!");


    }

    @RequestMapping("userActive/{uuid}")
    public String activeUser(@PathVariable("uuid") String uuid){
        String username =(String) redisTemplate.opsForValue().get("user" + uuid);

        TUser user = userService.getUserByUsername(username);
        user.setFlag(true);
        int i = userService.updateByPrimaryKeySelective(user);
        if(i>0){
            return "login";
        }else {
            return "register";
        }

    }




    @RequestMapping("userInfo")

    public String userInfo(){
        return "userInfo";


    }


}
