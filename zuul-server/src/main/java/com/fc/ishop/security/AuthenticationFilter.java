package com.fc.ishop.security;

/*import com.fc.ishop.cache.Cache;
import com.fc.ishop.cache.CachePrefix;
import com.fc.ishop.enums.UserEnums;
import com.fc.ishop.security.enums.SecurityEnum;
import com.fc.ishop.utils.ResponseUtil;
import com.fc.ishop.utils.StringUtils;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;*/

/**
 * 拦截第三关 角色权限
 * @author florence
 * @date 2023/12/2
 */
// @Slf4j extends BasicAuthenticationFilter
public class AuthenticationFilter  {
    /*private final Cache<String> cache;
    public AuthenticationFilter(AuthenticationManager authenticationManager, Cache<String> cache) {
        super(authenticationManager);
        this.cache = cache;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("spring security 接收到请求 {}",request.getRequestURL().toString());
        // 获取请求头信息
        String userToken = request.getHeader(SecurityEnum.HEADER_TOKEN.getValue());
        // 如果没有token返回
        if (StringUtils.isBlank(userToken)) {
            chain.doFilter(request, response);
            return;
        }
        // 获取用户信息
        UsernamePasswordAuthenticationToken authentication = getAuthentication(userToken, request, response);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String userInfo,HttpServletRequest  request,  HttpServletResponse response) {
        String json = cache.get(CachePrefix.ACCESS_TOKEN.getPrefix(UserEnums.MANAGER) + userInfo);
        // 在请求中添加数据
        request.setAttribute(SecurityEnum.AUTHENTICATED.getValue(), json);
        if (json != null) {
            AuthUser authUser = new Gson().fromJson(json, AuthUser.class);
            // 用户角色
            List<GrantedAuthority> auths = new LinkedList<>();
            auths.add(new SimpleGrantedAuthority("ROLE_" + authUser.getRole().name()));
            //构造返回信息
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(authUser.getUsername(), null, auths);
            authentication.setDetails(authUser);
            return authentication;
        }
        ResponseUtil.output(response, 403, ResponseUtil.resultMap(false, 403, "登录已失效，请重新登录"));
        return null;
    }*/
}
