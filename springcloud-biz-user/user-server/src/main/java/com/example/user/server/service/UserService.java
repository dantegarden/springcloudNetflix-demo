package com.example.user.server.service;

import com.example.user.server.entity.UserInfo;


public interface UserService {

	/**
	 * 通过token来查询用户信息
	 * @param token
	 * @return
	 */
	UserInfo findByToken(String token);
}
