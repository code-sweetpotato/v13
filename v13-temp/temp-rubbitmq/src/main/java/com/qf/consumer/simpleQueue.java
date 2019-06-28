package com.qf.consumer;

import com.rabbitmq.client.*;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class simpleQueue {
    //1.创建连接

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("106.14.57.231");
        connectionFactory.setVirtualHost("/javaee1902");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("javaee1902");
        connectionFactory.setPassword("123");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String result = new String(body,"utf-8");
                System.out.println(result);

            }
        };
        channel.basicConsume("simple",true,consumer);
    }


}
