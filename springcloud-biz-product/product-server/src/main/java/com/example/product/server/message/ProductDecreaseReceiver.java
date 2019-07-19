package com.example.product.server.message;

import com.example.product.common.dto.DecreaseStockInput;
import com.example.product.common.dto.OrderDecreaseStockInput;
import com.example.product.common.dto.ProductInfoOutput;
import com.example.product.server.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableBinding(ProductStreamClient.class)
@Slf4j
public class ProductDecreaseReceiver {

    @Autowired
    private ProductService productService;

    //接收消息
    @StreamListener(ProductStreamClient.INPUT)
    @SendTo(ProductStreamClient.ACK)
    public String receive(OrderDecreaseStockInput orderDecreaseStockInput){
        log.info("ProductDecreaseReceiver-Input:{}", orderDecreaseStockInput.getOrderId());
        productService.decreaseStock(orderDecreaseStockInput.getDecreaseStockInputList());
        log.info("ProductDecreaseReceiver-Input:消费完毕");
        return orderDecreaseStockInput.getOrderId();
    }
}
