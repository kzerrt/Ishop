package com.fc.ishop.interceptor;

import com.fc.ishop.cache.Cache;
import com.fc.ishop.constant.ReqParam;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.enums.UserEnums;
import com.fc.ishop.exception.TokenException;
import com.fc.ishop.prop.IgnoreProperties;
import com.fc.ishop.security.AuthUser;
import com.fc.ishop.security.context.UserContext;
import com.fc.ishop.security.enums.SecurityEnum;
import com.fc.ishop.utils.StringUtils;
import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * zuul网络拦截器
 * 请求拦截第二关 token filter
 * todo 判断token是否合理，添加用户信息请求头
 * @author florence
 * @date 2023/12/2
 */
@Slf4j
public class TokenInterceptor extends ZuulFilter {
    private final IgnoreProperties ignoreProperties;
    public TokenInterceptor(IgnoreProperties ignoreProperties) {
        this.ignoreProperties = ignoreProperties;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return -4;
    }

    @Override
    public boolean shouldFilter() {
        return true; // 是否启用过滤器
    }

    @Override
    public Object run() throws ZuulException {
        //log.debug("请求进入token filter");
        RequestContext ctx = RequestContext.getCurrentContext();
        // 获取请求头
        HttpServletRequest request = ctx.getRequest();
        String requestURI = request.getRequestURI();
        if (StringUtils.matchUri(requestURI, ignoreProperties.getUrls())) {// 判断是否是可忽略uri
            ctx.set(ReqParam.ignore);
            log.info("请求 {} 方行", requestURI);
            return null;
        }
        String accessToken = request.getHeader(SecurityEnum.HEADER_TOKEN.getValue());
        // 判断请求头是否存在
        if (StringUtils.isBlank(accessToken)) {
            throw new ZuulException(ResultCode.USER_NOT_LOGIN.message(),500,"token错误");
        }
        // 用户认证通过
        String user = UserContext.getCurrentUserToStr(accessToken);
        if (user == null) {
            log.warn("token 获取不正确, 缓存获取不到信息");
            throw new ZuulException(ResultCode.USER_NOT_LOGIN.message(),500,"token错误");
        }
        // 将用户信息以请求头方式发送
        ctx.addZuulRequestHeader(SecurityEnum.USER_CONTEXT.getValue(), user);// 设置请求头
        //log.debug("{} userContext请求头设置成功 {}", SecurityEnum.USER_CONTEXT.getValue(), requestURI);
        ctx.set(SecurityEnum.USER_CONTEXT.getValue(), new Gson().fromJson(user, AuthUser.class));
        return null;
    }
}
