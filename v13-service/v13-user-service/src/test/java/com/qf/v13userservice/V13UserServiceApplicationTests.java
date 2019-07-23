package com.qf.v13userservice;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v13.api.IUserService;
import com.qf.v13.entity.TUser;
import com.qf.v13.pojo.ResultBean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V13UserServiceApplicationTests {
	@Autowired
	private IUserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Resource(name = "redisLua")
	private RedisTemplate redisTemplate;

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


	//整合Spring BCrypt
	@Test
	public void testBCrypt() {
		TUser user = new TUser();

		user.setUsername("BCrypt第三次测试");
		user.setPassword("zhangsan");
		user.setEmail("38997654@qq.com");
		user.setFlag(false);
		user.setIsadmin(false);
		String encode = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encode);
		userService.insert(user);
		System.out.println("加密成功!");
	}

	@Test
	public void testParseBCrypt() {
		TUser user = new TUser();

		user.setUsername("BCrypt第三次测试");
		user.setPassword("zhangsan");

		TUser resultUser = userService.getUserByUsername(user.getUsername());
		boolean result = bCryptPasswordEncoder.matches(user.getPassword(),resultUser.getPassword());
		if(result){
			System.out.println("密码正确!");
		}else {
			System.out.println("密码错误!");
		}

	}

	@Test
	public void testLua(){
		DefaultRedisScript<Boolean> script = new DefaultRedisScript<>();
		script.setScriptSource(new ResourceScriptSource(new ClassPathResource("testlua.lua")));
		script.setResultType(Boolean.class);
		Boolean result = (boolean) redisTemplate.execute(script, null);
		System.out.println(result);

	}


	@Test
	public void testLuaContainParam(){
		DefaultRedisScript<Boolean> script = new DefaultRedisScript<>();
		script.setScriptSource(new ResourceScriptSource(new ClassPathResource("luaParam.lua")));
		script.setResultType(Boolean.class);
		List<String> list = new ArrayList<>(2);
		list.add("testparam");
		list.add("success");
		Boolean result = (boolean) redisTemplate.execute(script, list);
		System.out.println(result);

	}

	@Test
	public void testLuaGet(){
		DefaultRedisScript<Byte[]> script = new DefaultRedisScript<>();
		script.setScriptSource(new ResourceScriptSource(new ClassPathResource("get.lua")));
		script.setResultType(Byte[].class);
		//List<String> list = new ArrayList<>(1);
		//list.add("testparam");

		byte[] result = (byte[]) redisTemplate.execute(script, null);
		System.out.println(new String(result));


	}




	@Test
	public void testSet(){

		Boolean result1 = redisTemplate.opsForValue().setIfAbsent("lock", "666", 60, TimeUnit.SECONDS);
		Boolean result2 = redisTemplate.opsForValue().setIfAbsent("lock", "888", 60, TimeUnit.SECONDS);
		System.out.println(result1);
		System.out.println(result2);


	}


	@Test
	public void testDel(){
		DefaultRedisScript<Boolean> script = new DefaultRedisScript<>();
		script.setScriptSource(new ResourceScriptSource(new ClassPathResource("del.lua")));
		script.setResultType(Boolean.class);
		Boolean result = (boolean) redisTemplate.execute(script, null);
		System.out.println(result);



	}

}
