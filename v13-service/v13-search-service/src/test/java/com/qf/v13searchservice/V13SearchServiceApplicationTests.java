package com.qf.v13searchservice;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v13.api.IProductService;
import com.qf.v13.api.ISearchService;
import com.qf.v13.entity.TProduct;
import com.qf.v13.pojo.PageResultBean;
import com.qf.v13.pojo.ResultBean;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest

public class V13SearchServiceApplicationTests {

	@Autowired
	private SolrClient solrClient;

	@Autowired
	private ISearchService searchService;

	@Reference
	private IProductService productService;




	@Test
	public void contextLoads() throws IOException, SolrServerException {
		SolrInputDocument document = new SolrInputDocument();
		document.setField("id", 1);
		document.setField("product_name", "华为旗舰手机");
		document.setField("product_price", 2889);
		document.setField("product_sale_point", "支持国产");
		document.setField("product_images", "123");
		solrClient.add(document);
		solrClient.commit();
		System.out.println("保存成功!");

	}

	@Test
	public void testdeleteAll() throws IOException, SolrServerException {
		solrClient.deleteByQuery("*:*");
		solrClient.commit();
		System.out.println("删除成功!");

	}

	@Test
	public void testCopy() {
		ResultBean resultBean = searchService.copyAll();
		System.out.println(resultBean.getStatusCode());
	}

	@Test
	public void testSearch() {
		String keyword = "程序员";
		PageResultBean pageResultBean = new PageResultBean(1, 1);
		pageResultBean.setCurrentPage(2);
		pageResultBean.setTotalCounts(searchService.getTotalCount(keyword));
		pageResultBean.setTotalPage(searchService.getTotalPages(pageResultBean.getPageSize(), pageResultBean.getTotalCounts()));
		//ResultBean resultBean = searchService.SearchByKeyword(keyword);
		ResultBean resultBean = searchService.searchByKeywordAndPaging(keyword, pageResultBean);
		PageResultBean<TProduct> data = (PageResultBean<TProduct>) resultBean.getData();
		List<TProduct> list = data.getResultList();
		for (TProduct product : list) {
			System.out.println(product.getName());
		}


		//System.out.println(data.size());


	}

	@Test
	public void testPageResultBean() {
		PageResultBean pageResultBean = new PageResultBean(1, 2);
		pageResultBean.setTotalPage(5);
		pageResultBean.setCurrentPage(1);
		pageResultBean.setNavigatePage(3);
		System.out.println(pageResultBean.getNavigatePageNums().length);

		int[] nums = pageResultBean.getNavigatePageNums();
		for (int y = 0; y < nums.length; y++) {
			System.out.println(nums[y]);
		}
	}

	@Test
	public void testService() {
//		long count = searchService.getTotalCount("乐事");
//		System.out.println(count);
		TProduct product = productService.selectByPrimaryKey(5l);
		ResultBean resultBean = searchService.addInfoToSolr(product);
		System.out.println(resultBean.getStatusCode());

	}
}