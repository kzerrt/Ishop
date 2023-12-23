package com.fc.ishop.interceptor;

import com.fc.ishop.exception.IPException;
import com.fc.ishop.exception.TokenException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @author florence
 * @date 2023/12/3
 */
@Slf4j
public class ExceptionHandlerInterceptor extends ZuulFilter {
    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //log.debug("进入到全局异常处理");
        RequestContext currentContext = RequestContext.getCurrentContext();
        Throwable throwable = currentContext.getThrowable();
        if (throwable instanceof IPException) {
            currentContext.setResponseStatusCode(403);
        } else if (throwable instanceof TokenException) {
            currentContext.setResponseStatusCode(401);// 身份认证未通过
        } else {
            currentContext.setResponseStatusCode(404);
        }
        HttpServletRequest request = currentContext.getRequest();
        log.debug("请求出错 method: {}, uri:{} 请求错误消息 {}",
                request.getMethod(), request.getRequestURI(), throwable.getCause());
        //throwable.printStackTrace();
        // 可选：设置响应内容为一条错误消息
        currentContext.setResponseBody(throwable.getMessage());
        // 停止路由，不再将请求转发到后端服务
        currentContext.setSendZuulResponse(false);
        return null;
    }
}
