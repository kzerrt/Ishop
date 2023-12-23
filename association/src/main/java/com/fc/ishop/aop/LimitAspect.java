package com.fc.ishop.aop;

import com.fc.ishop.utils.IPUtil;
import com.fc.ishop.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 流量拦截
 * @author florence
 * @date 2023/12/5
 */
@Aspect
@Configuration
@Slf4j
public class LimitAspect {
    private RedisTemplate<String, Serializable> redisTemplate;
    // lua 脚本类
    private DefaultRedisScript<Number> limitScript;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setLimitScript(DefaultRedisScript<Number> limitScript) {
        this.limitScript = limitScript;
    }


    @Around("@annotation(com.fc.ishop.aop.LimitPoint)")
    public Object interceptor(ProceedingJoinPoint joinPoint){
        // 获取方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        HttpServletRequest request =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        LimitPoint limitAnnotation = method.getAnnotation(LimitPoint.class);
        LimitType limitType = limitAnnotation.limitType();
        // 注解名称
        String name = limitAnnotation.name();
        // 过期时间
        int period = limitAnnotation.period();
        // 限制次数
        int limitCount = limitAnnotation.count();
        String key;
        switch (limitType) {
            case IP:
                key = limitAnnotation.key() + IPUtil.getIpAddress(request);
                break;
            case CUSTOMER:
                key = limitAnnotation.key();
                break;
            default:
                key = StringUtils.upperCase(method.getName());
        }
        List<String> keys = Collections.singletonList(StringUtils.joinStr(limitAnnotation.prefix(), key));
        log.debug("Access keys is {}", keys);
        try {
            // 获取当前ip登录限制次数
            Number curCount = redisTemplate.execute(limitScript, keys, limitCount, period);
            log.info("Access try count is {} for name={} and key = {}", curCount, name, key);
            if (curCount != null && curCount.intValue() <= limitCount) { // 符合条件放行
                return joinPoint.proceed();
            } else {
                throw new RuntimeException("服务器异常，请稍后再试");
            }
        } catch (Throwable t) {
            t.printStackTrace();
            throw new RuntimeException("服务器异常，请稍后再试");

        }

    }

}
