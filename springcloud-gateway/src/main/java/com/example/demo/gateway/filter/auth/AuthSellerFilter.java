package com.example.demo.gateway.filter.auth;

import com.example.biz.common.core.enums.HttpStatusCodeEnum;
import com.example.biz.common.core.utils.JsonUtil;
import com.example.biz.common.web.utils.CookieUtil;
import com.example.user.common.dto.UserInfoDTO;
import com.example.user.common.enums.RoleEnum;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class AuthSellerFilter extends ZuulFilter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    //是否拦截
    @Override
    public boolean shouldFilter() {
        //获取request对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        if("/api/order/order/finish".equals(request.getRequestURI())){
            return true;
        }
        return false;
    }

    /**
     * /order/finish 卖家接单 只能卖家访问 （cookie里有X-Token，value是token_uuid_usertype, usertype是BUYER，把value当key去redis里拿token）
     * ***/

    @Override
    public Object run() throws ZuulException {
        //获取request对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        Cookie cookie = CookieUtil.get(request, "X-Token");
        if(cookie == null || StringUtils.isEmpty(cookie.getValue())){
            reject(requestContext, HttpStatusCodeEnum.LIMIT_ERROR);
        }else{
            String userInfoJson = stringRedisTemplate.opsForValue().get(cookie.getValue());
            if(StringUtils.isBlank(userInfoJson)){
                reject(requestContext, HttpStatusCodeEnum.LIMIT_ERROR);
            }else{
                UserInfoDTO userInfoDTO = (UserInfoDTO) JsonUtil.fromJson(userInfoJson, UserInfoDTO.class);
                if(!userInfoDTO.getRole().equals(RoleEnum.SELLER.getType())){
                    reject(requestContext, HttpStatusCodeEnum.NO_AUTH);
                }
            }
        }

        return null;
    }

    /**拒绝转发*/
    private void reject(RequestContext requestContext, HttpStatusCodeEnum httpStatusCodeEnum){
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(httpStatusCodeEnum.getCode());
    }
}
