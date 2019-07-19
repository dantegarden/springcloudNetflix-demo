package com.example.order.server.message;

import com.example.order.server.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(OrderStreamClient.class)
@Slf4j
public class OrderStatusReceiver {

    @Autowired
    private OrderService orderService;

    //接收消息
    @StreamListener(OrderStreamClient.INPUT)
    public void receiveAck(String orderId){
        log.info("OrderStatusReceiver-Input:{}", orderId);
        orderService.changeOrderStatus(orderId);
        log.info("OrderStatusReceiver-Input:消费完毕");
    }
}
