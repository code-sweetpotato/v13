package com.qf.v13emailservice;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v13.api.IEmailService;
import com.qf.v13.pojo.ResultBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V13EmailServiceApplicationTests {
	@Reference
	private IEmailService emailService;

	@Resource(name = "redisTemplateKeySerializer")
	private RedisTemplate redisTemplate;


	@Test
	public void contextLoads() {
		String email = "495296062@qq.com";
		String code = emailService.sendCode(email);
		System.out.println("发送的验证码为:"+code);
	}

	@Test
	public void testRandom(){
		String email = "495296062@qq.com";

		ResultBean resultBean = emailService.sendActiveMessage(email, "111", "账号激活");
		System.out.println(resultBean.getData().toString());
	}

	@Test
	public void testRedis(){
		redisTemplate.opsForValue().set("test","success");
		redisTemplate.opsForValue().set("k4","v4");
		System.out.println("操作成功!");
	}

	@Test
	public void testget(){
		String test = redisTemplate.opsForValue().get("test").toString();
		System.out.println(test);

	}

}
