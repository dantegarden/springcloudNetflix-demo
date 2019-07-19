package com.example.user.common.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    BUYER(1, "买家"),
    SELLER(2, "卖家");

    private Integer type;
    private String name;

    RoleEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
}
