package com.example.user.server.service.impl;

import com.example.user.server.dao.UserInfoRepostory;
import com.example.user.server.entity.UserInfo;
import com.example.user.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoRepostory userInfoRepostory;

    @Override
    public UserInfo findByToken(String token) {
        return userInfoRepostory.findByToken(token);
    }
}
