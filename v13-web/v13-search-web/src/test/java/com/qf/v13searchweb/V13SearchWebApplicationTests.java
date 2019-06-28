package com.qf.v13searchweb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v13.api.ISearchService;
import com.qf.v13.entity.TProduct;
import com.qf.v13.pojo.ResultBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V13SearchWebApplicationTests {

	@Reference
	private ISearchService searchService;

	@Test
	public void contextLoads() {
		ResultBean<List<TProduct>> resultBean = searchService.SearchByKeyword("程序员");
		System.out.println(resultBean.getData().size());
	}

}
