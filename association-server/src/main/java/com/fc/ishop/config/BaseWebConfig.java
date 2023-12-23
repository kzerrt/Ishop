package com.fc.ishop.config;

import com.fc.ishop.interceptor.BaseInterceptor;
import com.fc.ishop.prop.IgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 添加springmvc拦截器（拦截器存在于interceptor包中）
 *
 * @author florence
 * @date 2023/12/2
 */
@Configuration
public class BaseWebConfig implements WebMvcConfigurer {
    @Autowired
    private IgnoreProperties ignoreProperties;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BaseInterceptor(ignoreProperties));
    }
}
