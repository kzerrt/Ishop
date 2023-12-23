package com.fc.ishop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author florence
 * @date 2023/11/22
 */
//@Configuration
public class SecurityBean {

    /*@Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence == null ? "" : charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                if (charSequence == null || charSequence.length() == 0) {
                    return false;
                }
                return charSequence.equals(s);
            }
        };
    }*/
    /**
     * 定义跨域配置
     *
     * @return bean
     */
    /*@Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(CorsConfiguration.ALL);
        config.addAllowedHeader(CorsConfiguration.ALL);
        config.addAllowedMethod(CorsConfiguration.ALL);
        config.setMaxAge(1800L);
        source.registerCorsConfiguration("/**", config);
        return source;
    }*/
}
