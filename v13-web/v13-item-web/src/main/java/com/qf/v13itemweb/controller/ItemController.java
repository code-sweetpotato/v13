package com.qf.v13itemweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v13.api.IProductService;
import com.qf.v13.entity.TProduct;
import com.qf.v13.pojo.ResultBean;
import com.qf.v13.utils.HttpClientUtil;
import com.qf.v13itemweb.theadpolo.CreateHTMLTask;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Controller
@RequestMapping("item")
public class ItemController {

    @Autowired
    private Configuration configuration;

    @Reference
    private IProductService productService;


    @Autowired
    private ThreadPoolExecutor pool;

    @RequestMapping("createHTMlById/{id}")
    @ResponseBody
    public ResultBean createHTMl(@PathVariable long id){
        return createHTMLByID(id);
    }

    private ResultBean createHTMLByID(@PathVariable long id) {



        Template template = null;
        try {
            template = configuration.getTemplate("item.html");
            //输出数据,通过map集合,便于前端用k-v对取值
            Map<String,Object> data = new HashMap<>();

            TProduct product = productService.selectByPrimaryKey(id);
            data.put("product",product);
            //得到输出流,封装的是文件
            String serverPath = ResourceUtils.getURL("classpath:static").getPath();

            String filePath = serverPath+"/"+id+".html";
            Writer out = new FileWriter(filePath);

            try {
                template.process(data,out);
            } catch (TemplateException e) {
                e.printStackTrace();
                return new ResultBean("500","生成静态模板失败!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResultBean("500","没有找到模板页面对象!");
        }


        return new ResultBean("200","生成静态模板成功!");
    }

    //批量生成静态页面的方法
    @RequestMapping("batchCreateHTML")
    public ResultBean batchCreateHTML(@RequestParam("ids") List<Long> ids) {
        /*生成多个静态页面的无需按id的顺序进行生成,改进用多线程进行*/

        List<Future> list = new ArrayList<>(ids.size());
        for (Long id : ids) {
            //将返回的结果放在集合中

            list.add(pool.submit(new CreateHTMLTask(id)));

        }
        for (Future future : list) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();

            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return new ResultBean("200","批量生成页面成功!");

    }
}
