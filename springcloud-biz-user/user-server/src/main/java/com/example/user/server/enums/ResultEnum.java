package com.example.user.server.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    LOGIN_FAIL(1, "登陆失败"),

    LOGIN_SUCCESS(0, "登陆成功");


    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
