package com.qf.v13userservice;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v13.api.IUserService;
import com.qf.v13.entity.TUser;
import com.qf.v13.pojo.ResultBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V13UserServiceApplicationTests {
	@Autowired
	private IUserService userService;

	@Test
	public void contextLoads() {
		String email = "495062@qq.com";
		String username = "aa";
		System.out.println(userService.isExistEmail(email));


	}

	@Test
	public void testRegister() {
		TUser user = new TUser();
		user.setUsername("test");
		user.setPassword("123");
		user.setEmail("11111");
		userService.insert(user);
		System.out.println("注册成功!");


	}

	@Test
	public void testSelect() {
		String password = "1234";
		String username = "aa";


	}


	@Test
	public void testGetUserByUsername() {
		String password = "1234";
		String username = "122";
		TUser user = userService.getUserByUsername(username);
		System.out.println(user == null);

	}

	@Test
	public void testSelectUser() {
		TUser user = new TUser();
		user.setPassword("123");
		user.setUsername("aa");
		ResultBean resultBean = userService.selectByUsername(user);
		System.out.println(resultBean.getStatusCode());
		System.out.println(resultBean.getData());

	}

}
