package com.example.product.server.message;

import com.example.product.common.dto.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MqReceiver {

    //@RabbitListener(queues = "myQueue") //需要手动去mq控制台创建队列
    //@RabbitListener(queuesToDeclare = @Queue("myQueue"))  //启动时若队列不存在，自动创建队列
    @RabbitListener(bindings = @QueueBinding(
            value=@Queue("computerOrder"),
            key = "computer",
            exchange = @Exchange("myOrder"))) //启动时自动创建Queue和Exchange并绑定
    public void process(ProductInfoOutput message){
      log.info("MqReceiver-computer:{}", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value=@Queue("fruitOrder"),
            key = "fruit",
            exchange = @Exchange("myOrder"))) //启动时自动创建Queue和Exchange并绑定
    public void process2(String message){
        log.info("MqReceiver-fruit:{}", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value=@Queue("clothesOrder"),
            key = "clothes",
            exchange = @Exchange("myOrder"))) //启动时自动创建Queue和Exchange并绑定
    public void process3(String message){
        log.info("MqReceiver-clothes:{}", message);
    }
}
