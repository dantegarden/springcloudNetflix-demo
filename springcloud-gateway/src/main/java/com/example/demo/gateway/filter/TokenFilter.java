package com.example.demo.gateway.filter;

import com.example.biz.common.core.enums.HttpStatusCodeEnum;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
@Slf4j
public class TokenFilter extends ZuulFilter {

    //过滤器类型
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    //执行顺序，值越小越先执行
    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    //预处理业务逻辑
    @Override
    public Object run() throws ZuulException {
        //获取request对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        //从url参数里获取，也可以从cookie,header里获取
        String token = request.getParameter("token");
        log.info("请求写到token:{}", token);
        if(StringUtils.isBlank(token)){
            //没有token，不转发，返回401
//            requestContext.setSendZuulResponse(false);
//            requestContext.setResponseStatusCode(HttpStatusCodeEnum.LIMIT_ERROR.getCode());
        }
        /**这里可以通过token再去redis或数据库拿用户，看他有没有权限**/
        return null;
    }
}
