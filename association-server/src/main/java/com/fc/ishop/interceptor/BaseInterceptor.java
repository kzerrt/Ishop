package com.fc.ishop.interceptor;

import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.prop.IgnoreProperties;
import com.fc.ishop.security.AuthUser;
import com.fc.ishop.security.context.UserContext;
import com.fc.ishop.security.enums.SecurityEnum;
import com.fc.ishop.utils.StringUtils;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;

/**
 * 拦截器，拦截不正规权限
 * @author florence
 * @date 2023/12/2
 */
@Slf4j
public class BaseInterceptor implements HandlerInterceptor {
    private IgnoreProperties ignoreProperties;

    public BaseInterceptor() {
    }

    public BaseInterceptor(IgnoreProperties ignoreProperties) {
        this.ignoreProperties = ignoreProperties;
        if (ignoreProperties == null) {
            log.warn("IgnoreProperties is null");
        }
        log.info("ignoreProperties size is {}", ignoreProperties.getUrls().size());
    }

    // 在处理方法前调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (checkUrl(request)) { // 符合用户忽略请求
            log.debug("request : {} 放行", request.getRequestURI());
            return true;
        }
        // 判断请求是否可以被放行
        //log.debug("请求进入到springmvc拦截器中 {}", request);
        // 判断认证请求
        String authentication = request.getHeader(SecurityEnum.AUTHENTICATED.getValue());
        String user = request.getHeader(SecurityEnum.USER_CONTEXT.getValue());
        //log.debug("header : {}, param :{}", accessToken, authentication);
        if (StringUtils.isEmpty(authentication) || !"true".equals(authentication)) {
            log.error("请求 {} 被拦截， 请求参数不正确. userContext : {}, authentication : {}",
                    request.getRequestURI(), user, authentication);
            return false;
        }
        //String user = request.getHeader(SecurityEnum.USER_CONTEXT.getValue());
        if (StringUtils.isEmpty(user)) {
            log.warn("未获取到用户信息 {}", request.getRequestURI());
            return false;
        }
        try {
            UserContext.setCurrentUser(new Gson().fromJson(user, AuthUser.class));
        } catch (Exception e) {
            log.warn(" user : {} 用户信息转换失败!! {}", user, e.getMessage());
            return false;
        }
        // 添加用户信息
        return true;
    }
    // 在处理方法执行后、视图渲染前调用
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
    // 请求完成后调用
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if ("OPTION".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        // 将用户信息删除
        UserContext.removeCurrentUser();
    }
    private boolean checkUrl(HttpServletRequest request) {
        if (request == null) {
            throw new ServiceException(ResultCode.PARAMS_ERROR);
        }
        if (ignoreProperties == null || ignoreProperties.getUrls().size() < 1) {
            return false;
        }
        // 获取uri
        String requestURI = request.getRequestURI();

        return StringUtils.matchUri(requestURI, ignoreProperties.getUrls());
    }
}
