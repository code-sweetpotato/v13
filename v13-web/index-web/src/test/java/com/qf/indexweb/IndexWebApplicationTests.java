package com.qf.indexweb;

import com.qf.v13.utils.HttpClientUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexWebApplicationTests {



	@Test
	public void contextLoads() {

	}

	@Test
	public void testHttpUtil(){
		long id = 2l;
		String path = "http://localhost:9093/item/createHTMlById"+id;
		HttpClientUtil.doGet(path);
		System.out.println("生成成功!");

	}


}
