package com.qf.v13miaosha;

import com.qf.v13miaosha.entity.TProduct;
import com.qf.v13miaosha.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V13MiaoshaApplicationTests {

	@Autowired
	private ProductService productService;

	@Test
	public void contextLoads() {


	}

}
