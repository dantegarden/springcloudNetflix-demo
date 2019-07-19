package com.example.user.server.dao;

import com.example.user.server.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 廖师兄
 * 2018-03-04 21:42
 */
public interface UserInfoRepostory extends JpaRepository<UserInfo, String> {

	UserInfo findByToken(String token);
}
