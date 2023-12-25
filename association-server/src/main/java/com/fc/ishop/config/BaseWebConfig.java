package com.fc.ishop.config;

import com.fc.ishop.base.DateConvert;
import com.fc.ishop.interceptor.BaseInterceptor;
import com.fc.ishop.prop.IgnoreProperties;
import com.fc.ishop.utils.DateUtil;
import com.fc.ishop.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
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

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter((String date) -> {
            if (StringUtils.isEmpty(date)) {
                return null;
            }
            return DateUtil.toDate(date, DateUtil.STANDARD_DATE_FORMAT);
        });
    }
}
