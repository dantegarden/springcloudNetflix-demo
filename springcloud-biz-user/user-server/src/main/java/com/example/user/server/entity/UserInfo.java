package com.example.user.server.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class UserInfo {

	@Id
	private String id;

	private String username;

	private String password;

	private String token;

	private Integer role;
}
