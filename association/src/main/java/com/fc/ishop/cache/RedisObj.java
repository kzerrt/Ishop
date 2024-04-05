package com.fc.ishop.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author florence
 * @date 2024/4/5
 */
@Component
public class RedisObj implements Cache {

    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public Object get(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public String getString(Object key) {
        try {
            return redisTemplate.opsForValue().get(key).toString();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void put(Object key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void put(Object key, Object value, Long exp) {
        put((String)key, value, exp, TimeUnit.SECONDS);
    }

    @Override
    public void put(String key, Object value, Long exp, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, exp, timeUnit);
    }

    @Override
    public void remove(Object key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean hasKey(Object key) {
        return this.redisTemplate.opsForValue().get(key) != null;
    }

    @Override
    public List<String> keys(String pattern) {
        return null;
    }
}
