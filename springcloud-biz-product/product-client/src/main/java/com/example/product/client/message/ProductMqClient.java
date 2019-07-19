package com.example.product.client.message;

import com.example.product.common.dto.ProductInfoOutput;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductMqClient {
    @Autowired
    private AmqpTemplate amqpTemplate;


    public void sendMessage(ProductInfoOutput productInfoOutput){
        amqpTemplate.convertAndSend("myOrder", "computer", productInfoOutput);
    }
}
