package com.fc.ishop.interceptor;

import com.fc.ishop.constant.ReqParam;
import com.fc.ishop.exception.IPException;
import com.fc.ishop.utils.IPUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求限制第一关,查询ip是否被限制
 * @author florence
 * @date 2023/12/2
 */
@Slf4j
public class IPInterceptor extends ZuulFilter{
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return -5;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // todo 动态获取限制ip
        // 获取host信息
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String host = IPUtil.getIpAddress(request);
        //log.debug("请求进入到ip拦截器 {} request method: {} request uri: {}", host,request.getMethod(), request.getRequestURI());
        if (IPUtil.checkUrl(request.getRequestURI())) {
            context.setSendZuulResponse(false);
        }
        if (IPUtil.hasLimitedIp(host)) {
            log.warn("主机：{} 被限制登录", host);
            throw new IPException("请求被拒绝，请稍后重试");
        }
        return null;
    }

}
