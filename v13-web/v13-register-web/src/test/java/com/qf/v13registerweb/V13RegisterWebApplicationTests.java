package com.qf.v13registerweb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V13RegisterWebApplicationTests {

	@Resource(name = "redisTemplateKeySerializer")
	private RedisTemplate redisTemplate;


	@Test
	public void contextLoads() {

		System.out.println("邮件发送成功!");

	}

	@Test
	public void testRedis() {


		String username = redisTemplate.opsForValue().get("user" + "9c86955f-4e03-46e5-9fc1-d12815392c17").toString();
		System.out.println(username);

	}



}
