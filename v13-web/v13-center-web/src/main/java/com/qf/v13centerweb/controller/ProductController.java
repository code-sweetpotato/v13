package com.qf.v13centerweb.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.qf.v13.api.IProductService;
import com.qf.v13.api.IProductTypeService;
import com.qf.v13.api.ISearchService;
import com.qf.v13.constant.RabbitQMConstant;
import com.qf.v13.entity.TProduct;
import com.qf.v13.entity.TProductType;
import com.qf.v13.pojo.ProductVO;
import com.qf.v13.pojo.ResultBean;
import com.qf.v13.utils.HttpClientUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.*;

@Controller
@RequestMapping("productService")
public class ProductController {

    @Reference
    private IProductService productService;

    @Reference
    private IProductTypeService productTypeService;

    @Reference
    private ISearchService searchService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ResponseBody
    @RequestMapping("test")
    public TProduct hello(){
        TProduct product = productService.selectByPrimaryKey(1L);
        return product;
    }


    //传入当前页和每页展示的个数
    @RequestMapping("list/{pageNum}/{pageSize}")
    public String list(@PathVariable int pageNum,@PathVariable int pageSize, Model model){
        PageInfo pageInfo = productService.getPage(pageNum, pageSize);
        //将东西存起来
        model.addAttribute("pageInfo",pageInfo);
        return "product/list";

    }


    //添加商品信息
    @PostMapping("save")
    public String save(ProductVO vo){

        long id = productService.save(vo);
        rabbitTemplate.convertAndSend(RabbitQMConstant.CENTER_EXCHANGE,"product.save",id);
/*        vo.getProduct().setId(id);
        TProduct product = vo.getProduct();
        searchService.addInfoToSolr(product);
        String path = "http://localhost:9093/item/createHTMlById/"+id;
        HttpClientUtil.doGet(path);*/

        return "redirect:/productService/list/1/2";

    }
    //删除商品信息
    @PostMapping("batchDel")
    @ResponseBody
    public ResultBean batchDel(@RequestParam List<Long> ids){

        int i = productService.batchDel(ids);
        //搜索系统更改
        int z = searchService.batchDel(ids);
        //详情系统更改
/*        String path = "http://localhost:9093/item/batchCreateHTML";
        Map<String,Object> map = new HashMap<>();
        map.put("ids",ids);
        String result = HttpClientUtil.doPost(path, map);*/


        if(i>0&&z>0){
            ResultBean resultBean = new ResultBean("200","删除成功!");
            return resultBean;
        }else {
            ResultBean resultBean = new ResultBean("404","删除失败");
            return resultBean;
        }


    }

    @PostMapping("deleteById")
    @ResponseBody
    public ResultBean deleteById(@RequestParam Long id){
        int i = productService.deleteByPrimaryKey(id);
        if(i>0){
            ResultBean resultBean = new ResultBean("200","删除成功!");

            return resultBean;
        }
        ResultBean resultBean = new ResultBean("404", "删除失败");
        return resultBean;
    }

    @PostMapping("productTypeList")
    @ResponseBody
    public ResultBean getProductTypeList(){
        List<TProductType> list = productTypeService.list();

        if(list != null){
            return new ResultBean("200",list);
        }
        return new ResultBean("404",null);
    }


    //修改商品信息
    @PostMapping("update")
    public String updata(TProduct product){

        productService.updateByPrimaryKeySelective(product);


        long id = product.getId();
        rabbitTemplate.convertAndSend(RabbitQMConstant.CENTER_EXCHANGE,"product_save",id);
/*
        searchService.addInfoToSolr(product);
       String path = "http://localhost:9093/item/createHTMlById/"+id;
        HttpClientUtil.doGet(path);
*/

        return "redirect:/productService/list/1/2";

    }


    //查询商品信息
    @PostMapping("getById")
    @ResponseBody
    public ResultBean selectById(long id){

        TProduct product = productService.selectByPrimaryKey(id);

        if(product !=null){
            return new ResultBean("200",product);
        }else {
            return new ResultBean("404","商品信息不存在");
        }



    }



}
