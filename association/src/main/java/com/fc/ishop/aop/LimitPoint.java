package com.fc.ishop.aop;

import java.lang.annotation.*;

/**
 * 限流注解
 * @author florence
 * @date 2023/12/5
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LimitPoint {
    /**
     * 资源名称 无实际意义， 可以用户排除异常
     * @return
     */
    String name() default "";

    /**
     * 资源 key
     * 如果下方 limitType 值为自定义，那么全局限流参数来自于此
     * 如果limitType 为ip，则限流条件 为 key+ip
     * @return
     */
    String key() default "";

    /**
     * key的前缀，可选
     * @return
     */
    String prefix() default "";

    /**
     * 时间单位 s
     * @return
     */
    int period() default 60;

    /**
     * 限制次数
     * @return
     */
    int count() default 3;

    LimitType limitType() default LimitType.IP;


}
