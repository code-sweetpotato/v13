package com.qf.v13itemweb;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V13ItemWebApplicationTests {

	@Autowired
	private Configuration configuration;

	@Test
	//freemaker测试
	public void contextLoads() throws IOException, TemplateException {
		Template template = configuration.getTemplate("Hello.html");
		Map<String,Object> data = new HashMap();
		data.put("name","zhangsan");

		Writer out = new FileWriter("D:\\IDEA_Projects\\v13\\v13-web\\v13-item-web\\src\\main\\resources\\templates\\hello.html");

		template.process(data,out);
		System.out.println("生成静态页面成功!");


	}

}
