package com.example.product.server.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ProductStreamClient {
    final String INPUT = "requestProductDecrease";
    final String ACK = "responseProductDecrease";

    @Input(ProductStreamClient.INPUT)
    SubscribableChannel input();

    @Output(ProductStreamClient.ACK)
    MessageChannel ack();
}
