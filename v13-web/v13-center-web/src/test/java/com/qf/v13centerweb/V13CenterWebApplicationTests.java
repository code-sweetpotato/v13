package com.qf.v13centerweb;

import com.github.tobato.fastdfs.FdfsClientConfig;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.v13.utils.HttpClientUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.ls.LSInput;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(FdfsClientConfig.class)
public class V13CenterWebApplicationTests {

	@Autowired
	private FastFileStorageClient client;



	@Test
	public void contextLoads() throws FileNotFoundException {
		File file = new File("D:\\HTML5\\3.jpg");
		//返回存储的路径
		StorePath storePath = client.uploadFile(new FileInputStream(file), file.length(), "jpg", null);
		System.out.println(storePath);
	}

	@Test
	public void testHttpUtil(){

	}

}
