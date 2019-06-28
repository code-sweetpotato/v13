package com.qf.v13loginweb;


import com.alibaba.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V13LoginWebApplicationTests {


	@Test
	public void contextLoads() {
		String uuid = "123";
		StringBuffer s = new StringBuffer("user_token_").append(uuid);
		System.out.println(s.toString());
	}

}
