package com.example.order.server.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface OrderStreamClient {
    final String OUTPUT = "requestProductDecrease";
    final String INPUT = "responseProductDecrease";

    @Output(OrderStreamClient.OUTPUT)
    MessageChannel output();

    @Input(OrderStreamClient.INPUT)
    SubscribableChannel recevieAck();
}
