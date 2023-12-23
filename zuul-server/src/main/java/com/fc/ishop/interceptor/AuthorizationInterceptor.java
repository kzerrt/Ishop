package com.fc.ishop.interceptor;

import com.fc.ishop.constant.ReqParam;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.enums.UserEnums;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.exception.TokenException;
import com.fc.ishop.security.AuthUser;
import com.fc.ishop.security.enums.SecurityEnum;
import com.fc.ishop.utils.StringUtils;
import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户授权接口
 * 权限限制访问
 * @author florence
 * @date 2023/12/4
 */
@Slf4j
public class AuthorizationInterceptor extends ZuulFilter {
    private final String member = "/member/**";
    private final String store = "/store/**";
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return -2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //log.debug("请求进入授权 filter");
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.getBoolean(ReqParam.ignore)) { // 访问公共页
            // todo 上层拦截到common网页资源，此处判断是否为该用户的图片
            return null;
        }
        if (!ctx.getBoolean(SecurityEnum.AUTHENTICATED.getValue())) {// 用户未授权
            throw new TokenException(ResultCode.USER_NOT_LOGIN.message());
        }
        HttpServletRequest request = ctx.getRequest();
        AuthUser authUser = (AuthUser) ctx.get(ReqParam.user);
        if (authUser == null) {
            return null;
        }
        String uri = request.getRequestURI();
        UserEnums role = authUser.getRole();
        if (UserEnums.STORE.equals(role)) {
            if (!StringUtils.matchUri(uri, member) || !StringUtils.matchUri(uri, store)) {
                throw new ServiceException(ResultCode.USER_AUTHORITY_ERROR);
            }
        } else if (UserEnums.MEMBER.equals(role)) {
            if (!StringUtils.matchUri(uri, member)) { // 会员只能在该请求路径下访问
                throw new ServiceException(ResultCode.USER_AUTHORITY_ERROR);
            }
        }
        // 设置认证通过请求头
        ctx.addZuulRequestHeader(SecurityEnum.AUTHENTICATED.getValue(), "true");
        //log.debug("{} 请求头设置成功 {}", SecurityEnum.AUTHENTICATED.getValue(), uri);
        return null;
    }
}
