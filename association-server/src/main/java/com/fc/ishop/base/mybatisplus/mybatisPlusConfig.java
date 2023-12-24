package com.fc.ishop.base.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus配置类
 * @author florence
 * @date 2023/11/28
 */
@Configuration
@MapperScan("com.fc.ishop.mapper")
public class mybatisPlusConfig {
    /**
     * 分页插件，自动识别数据库类型
     */
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        return new PaginationInnerInterceptor();
    }
}
