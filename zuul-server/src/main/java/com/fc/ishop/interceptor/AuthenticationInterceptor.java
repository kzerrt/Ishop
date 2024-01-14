package com.fc.ishop.interceptor;

import com.fc.ishop.constant.ReqParam;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.exception.TokenException;
import com.fc.ishop.security.AuthUser;
import com.fc.ishop.security.enums.SecurityEnum;
import com.fc.ishop.utils.StringUtils;
import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.cert.ocsp.Req;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户认证拦截
 * 未认证的用户不允许登录
 * 判断请求是否合法
 * @author florence
 * @date 2023/12/4
 */
@Slf4j
public class AuthenticationInterceptor extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return -3;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //log.debug("请求进入authentication filter");

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        // RequestContext.getCurrentContext().setRequest(); 新建一个请求
        if (ctx.getBoolean(ReqParam.ignore)) {// 公共请求 放行
            log.debug("公共请求放行- 添加认证请求头");
            ctx.addZuulRequestHeader(SecurityEnum.AUTHENTICATED.getValue(), "true");
            // todo 网关拦截用户图片，并判断该图片是否是该用户
            return null;
        }
        String accessToken = request.getHeader(SecurityEnum.HEADER_TOKEN.getValue());
        if (StringUtils.isEmpty(accessToken)) {
            throw new TokenException(ResultCode.USER_NOT_LOGIN.message());
        }
        //String user = request.getHeader(SecurityEnum.USER_CONTEXT.getValue());
        AuthUser authUser = (AuthUser) ctx.get(SecurityEnum.USER_CONTEXT.getValue());
        if (authUser == null) {
            throw new TokenException(ResultCode.USER_NOT_LOGIN.message());
        }
        ctx.set(ReqParam.user, authUser);
        ctx.set(SecurityEnum.AUTHENTICATED.getValue());
        return null;
    }
}
