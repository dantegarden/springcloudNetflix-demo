package com.example.user.common.dto;

import lombok.Data;

@Data
public class UserInfoDTO {

	private String id;

	private String username;

	private String password;

	private String token;

	private Integer role;
}
