package com.example.product.server.enums;

import lombok.Getter;

/**
 * Created by 廖师兄
 * 2017-12-10 23:00
 */
@Getter
public enum ExceptionEnum {

    PRODUCT_NOT_EXIST(1, "商品不存在"),
    PRODUCT_STOCK_ERROR(2, "库存有误"),
    ;

    private Integer code;

    private String message;

    ExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
