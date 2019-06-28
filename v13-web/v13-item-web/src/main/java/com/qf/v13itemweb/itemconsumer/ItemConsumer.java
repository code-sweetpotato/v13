package com.qf.v13itemweb.itemconsumer;

import com.qf.v13.constant.RabbitQMConstant;
import com.qf.v13.pojo.ResultBean;
import com.qf.v13.utils.HttpClientUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ItemConsumer {


    //生成静态页面
    @RabbitListener(queues = RabbitQMConstant.PRODUCT_ITEM_QUEUE_ADD)
    @RabbitHandler
    public void handlerAdd(long id){
        String path = "http://localhost:9093/item/createHTMlById/"+id;
        String result = HttpClientUtil.doGet(path);
        System.out.println(result);

    }
}
