package com.qf.v13productservice;

import com.alibaba.dubbo.rpc.filter.tps.TPSLimiter;
import com.github.pagehelper.PageInfo;
import com.qf.v13.api.IProductService;
import com.qf.v13.api.IProductTypeService;
import com.qf.v13.entity.TProduct;
import com.qf.v13.entity.TProductDesc;
import com.qf.v13.pojo.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.input.KeyCode.L;

@RunWith(SpringRunner.class)
@SpringBootTest

public class V13ProductServiceApplicationTests {

	@Autowired
	private IProductService productService;

	@Autowired
	private IProductTypeService productTypeService;

	@Autowired
	private DataSource dataSource;

	@Test
	public void contextLoads() {
		System.out.println(productTypeService.list().size());


	}
	@Test
	public void testDataSource() throws SQLException {
		System.out.println(dataSource.getConnection());
	}

	@Test
	public void testupdate(){
		TProduct product = new TProduct();
		product.setId(20L);
		product.setName("商品测试");
		productService.updateByPrimaryKeySelective(product);

	}

}
