package com.qf.v13searchweb.consumer;

import com.alibaba.dubbo.config.annotation.Reference;

import com.qf.v13.api.ISearchService;
import com.qf.v13.constant.RabbitQMConstant;
import com.qf.v13.entity.TProduct;
import com.qf.v13.pojo.ResultBean;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SearchConsumer {

    //编写方法接受相应的信息,并处理相应的信息

    @Reference
    private ISearchService searchService;

    @RabbitListener(queues = RabbitQMConstant.PRODUCT_SEARCH_QUEUE_ADD)
    @RabbitHandler
    public void handlerAdd(Long id){
         searchService.addInfoToSolrById(id);

    }



}
