package com.fc.ishop.filter;

import com.fc.ishop.security.enums.SecurityEnum;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * fegin拦截器
 * 将请求头传递。
 * 微服务中a服务调用b服务，a服务拦截请求，并将请求头放入到fegin中传递给b服务
 *
 * 在包security中注册
 * @author florence
 * @date 2023/12/1
 */
@Slf4j
@Configuration
public class FeignClientInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 获取对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            log.warn("FeignClientInterceptor can not found request headers");
            return;
        }
        // 请求对象
        HttpServletRequest request = requestAttributes.getRequest();
        // 获取当前请求的header
        Enumeration<String> headerNames = request.getHeaderNames();
        //System.out.println(headerNames);
        if (headerNames == null) {
            log.warn("FeignClientInterceptor can not found request headers");
            return;
        }
        byte count = 0;
        // 将header传递
        try {
            requestTemplate.header(SecurityEnum.AUTHENTICATED.getValue(),
                    request.getHeader(SecurityEnum.AUTHENTICATED.getValue()));
            requestTemplate.header(SecurityEnum.USER_CONTEXT.getValue(),
                    request.getHeader(SecurityEnum.USER_CONTEXT.getValue()));
            /*while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                if (set.contains(headerName)) {
                    requestTemplate.header(headerName, request.getHeader(headerName));
                    System.out.println(headerName + "  :  " + request.getHeader(headerName));
                    count++;
                    if (count == set.size()) {
                        break;
                    }
                }
            }*/
        } catch (Exception e) {
            log.error("FeignClientInterceptor get header error {}", request);
            e.printStackTrace();
        }
    }
}
