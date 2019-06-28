package com.qf.indexweb.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v13.api.IProductTypeService;
import com.qf.v13.entity.TProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.List;

@Controller
@RequestMapping("index")
public class IndexController {

    @Reference
    private IProductTypeService productTypeService;

    @RequestMapping("list")
    public String index(Model model){

        List<TProductType> list = productTypeService.list();
        model.addAttribute("list",list);
        return "home";
    }
}
