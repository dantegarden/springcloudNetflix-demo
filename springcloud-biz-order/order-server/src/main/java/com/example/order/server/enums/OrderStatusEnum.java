package com.example.order.server.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    WAIT(0, "等待下单"),
    NEW(1, "新订单"),
    FINISHED(2, "完结"),
    CANCEL(3, "取消"),
    ;
    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
