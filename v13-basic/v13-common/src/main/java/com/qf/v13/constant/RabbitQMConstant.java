package com.qf.v13.constant;

public interface RabbitQMConstant {
    //商品搜索系统的队列
    public static String PRODUCT_SEARCH_QUEUE_ADD = "product_search_queue_add";

    //平台系统对应的交换机
    public static String CENTER_EXCHANGE = "center_product_exchange";

    //商品静态页面系统的队列
    public static String PRODUCT_ITEM_QUEUE_ADD ="product_item_queue_add";

    //商品搜索修改系统的队列
    public static String PRODUCT_SEARCH_QUEUE_UPDATE = "product_search_queue_update";

    //商品修改的信息类型
    public static String PRODUCT_ROUTING_UPDATE = "product_routing_update";

    //用户注册的对应的userService的队列
    public static String USER_USERSERVICE_QUEUE_REGISTER = "user_userservice_queue_register";

    //用户注册对应的邮件服务的队列
    public static String USER_EMAILSERVICE_QUEUE_REGISTER = "user_emailservice_queue_register";

    //用户注册对应的信息类型
    public static String USER_ROUTING_REGISTER ="USER_ROUTING_REGISTER";
}
