package com.qf.v13searchweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v13.api.ISearchService;
import com.qf.v13.entity.TProduct;
import com.qf.v13.pojo.PageResultBean;
import com.qf.v13.pojo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("search")
public class SearchController {

    @Reference
    private ISearchService searchService;

    @RequestMapping("keyword/{keyword}/{currentPage}")
    public String searchByKeyword(@PathVariable String keyword,@PathVariable int currentPage,  Model model){
        PageResultBean<TProduct> pageResultBean = new PageResultBean<>(1,2);


        if(currentPage !=0){
            pageResultBean.setCurrentPage(currentPage);
        }

        //设置总条数
        pageResultBean.setTotalCounts(searchService.getTotalCount(keyword));
        //设置总页数
        pageResultBean.setTotalPage(searchService.getTotalPages(pageResultBean.getPageSize(),pageResultBean.getTotalCounts()));
        //设置导航页码数
        pageResultBean.setNavigatePage(3);



        ResultBean<PageResultBean<TProduct>> resultBean = searchService.searchByKeywordAndPaging(keyword, pageResultBean);

        // ResultBean<List<TProduct>> resultBean = searchService.SearchByKeyword(keyword);
        model.addAttribute("keyword",keyword);

        //查询数据成功
        model.addAttribute("result",resultBean);

        return "list";
    }
}
