package com.qf.v13.api;

import com.qf.v13.pojo.ResultBean;

public interface IEmailService {

    //向邮箱发送验证码的功能
    public String sendCode(String email);


    //向邮箱发送激活连接
    public ResultBean sendActiveMessage(String email,String activeText,String subject);
}
