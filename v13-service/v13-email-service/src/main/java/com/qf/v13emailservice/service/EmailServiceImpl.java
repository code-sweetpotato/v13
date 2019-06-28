package com.qf.v13emailservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.v13.api.IEmailService;
import com.qf.v13.pojo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
public class EmailServiceImpl implements IEmailService{

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.fromAddr}")
    private String from;

    //向邮箱发送验证码,返回其验证码给前端
    @Override
    public String sendCode(String email) {

        SimpleMailMessage message = new SimpleMailMessage();
        int random = (int) (Math.random()*10000);
        String code = Integer.toString(random);
        message.setText(code);
        message.setSubject("获得验证码");
        message.setTo(email);
        message.setFrom(from);
        mailSender.send(message);

        return code;
    }


    //向邮箱发送激活连接,通过uuid的唯一标识,识别激活那个用户
    @Override
    public ResultBean sendActiveMessage(String email, String activeText, String subject) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject(subject);
            UUID uuid = UUID.randomUUID();
            String text = activeText;
            helper.setText(text,true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            return new ResultBean("500","生成邮件信息失败!");
        }
        return new ResultBean("200","发送连接成功!");
    }
}
