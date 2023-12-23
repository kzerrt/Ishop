package com.fc.ishop.script;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * redis lua 脚本
 * @author florence
 * @date 2023/12/7
 */
@Configuration
public class RedisScript {
    @Bean
    public DefaultRedisScript<Number> limitScript() {
        DefaultRedisScript<Number> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/limit.lua")));
        redisScript.setResultType(Number.class);
        return redisScript;
    }
}
