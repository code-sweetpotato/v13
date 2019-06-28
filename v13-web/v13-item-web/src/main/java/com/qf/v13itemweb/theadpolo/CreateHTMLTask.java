package com.qf.v13itemweb.theadpolo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v13.api.IProductService;
import com.qf.v13.entity.TProduct;
import com.qf.v13.pojo.ResultBean;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class CreateHTMLTask implements Callable<Boolean> {

    private long id;

    @Autowired
    private Configuration configuration;

    @Reference
    private IProductService productService;


    public CreateHTMLTask(long id){
        this.id = id;
    }


    @Override
    public Boolean call() throws Exception {
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
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }
}
