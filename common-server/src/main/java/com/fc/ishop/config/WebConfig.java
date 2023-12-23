package com.fc.ishop.config;

import com.fc.ishop.prop.UploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 文件映射
 * @author florence
 * @date 2023/11/30
 */
@Slf4j
@Configuration
public class WebConfig extends BaseWebConfig {
    @Autowired
    private UploadProperties uploadProperties;
    private String maPath = "file: D:/coding/img/upload/image";// 文件映射地址
    private String staticAccess = "/upload/image/**";
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (uploadProperties != null) {
            staticAccess = uploadProperties.getStaticAccessPath();
            maPath = "file:" + uploadProperties.getImgAbsolutePath() + "/";
        }
        log.info("文件映射uri路径：{}， 文件路径： {}",staticAccess, maPath);
        registry.addResourceHandler(staticAccess)
                .addResourceLocations(maPath);
    }
}
