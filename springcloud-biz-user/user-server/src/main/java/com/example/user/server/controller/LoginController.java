package com.example.user.server.controller;

import com.example.biz.common.core.utils.JsonUtil;
import com.example.biz.common.web.bean.Result;
import com.example.biz.common.web.utils.CookieUtil;
import com.example.user.common.dto.UserInfoDTO;
import com.example.user.common.enums.RoleEnum;
import com.example.user.server.entity.UserInfo;
import com.example.user.server.enums.ResultEnum;
import com.example.user.server.properties.UserProperties;
import com.example.user.server.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {

    @Autowired
    private UserProperties userProperties;

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**统一登陆**/
    @GetMapping("/login")
    public Result userLogin(@RequestParam("token") String token, HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = CookieUtil.get(request, userProperties.getTokenKey());
        if(cookie!=null &&
                StringUtils.isNotBlank(stringRedisTemplate.opsForValue().get(cookie.getValue()))){
            return Result.ok();
        }else{
            //没登录
            //token和数据库里的数据匹配
            UserInfo userInfo = userService.findByToken(token);
            if(userInfo == null){
                return Result.fail(ResultEnum.LOGIN_FAIL.getMessage(), ResultEnum.LOGIN_FAIL.getCode());
            }

            //redis设置 key=token_UUID, value=userInfo
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            BeanUtils.copyProperties(userInfo, userInfoDTO);
            String uuid = setToken2Redis(userInfoDTO);
            //设置cookie x-token=UUID
            CookieUtil.set(response, userProperties.getTokenKey(), uuid, userProperties.getExpire());
            return Result.ok();
        }
    }

    private String setToken2Redis(UserInfoDTO userInfoDTO){
        String uuid = UUID.randomUUID().toString();
        String key = String.format("token_%s", uuid);
        Integer expire = userProperties.getExpire();
        stringRedisTemplate.opsForValue().set(key, JsonUtil.toJson(userInfoDTO), expire, TimeUnit.SECONDS);
        return key;
    }
}
