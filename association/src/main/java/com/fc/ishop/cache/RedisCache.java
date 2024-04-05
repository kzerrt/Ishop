package com.fc.ishop.cache;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Component
public class RedisCache implements Cache<String>, ApplicationContextAware {

    private static RedisCache redisCache;
    @Autowired
    private StringRedisTemplate redisTemplate;

    public RedisCache() {

    }
    public static RedisCache getCache() {
        return redisCache;
    }
    @Override
    public String get(Object key) {

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

    /*@Override
    public List multiGet(Collection keys) {
        return redisTemplate.opsForValue().multiGet(keys);

    }*/


    /*@Override
    public void multiSet(Map map) {
        redisTemplate.opsForValue().multiSet(map);
    }*/

    /*@Override
    public void multiDel(Collection keys) {
        redisTemplate.delete(keys);
    }*/

    @Override
    public void put(Object key, String value) {
        redisTemplate.opsForValue().set((String) key, value);
    }

    @Override
    public void put(Object key, String value, Long exp) {
        put((String)key, value, exp, TimeUnit.SECONDS);
    }

    @Override
    public void put(String key, String value, Long exp, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, exp, timeUnit);
    }

    @Override
    public void remove(Object key) {

        redisTemplate.delete((String) key);
    }

    /**
     * 删除
     *
     */
    /*@Override
    public void vagueDel(Object key) {
        Set<Object> keys = redisTemplate.keys(key + "*");
        redisTemplate.delete(keys);
    }*/

    /*@Override
    public void clear() {

        Set keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
    }

    @Override
    public void putHash(Object key, Object hashKey, Object hashValue) {
        redisTemplate.opsForHash().put(key, hashKey, hashValue);
    }

    @Override
    public void putAllHash(Object key, Map map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    @Override
    public Object getHash(Object key, Object hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    @Override
    public Map<Object, Object> getHash(Object key) {
        return this.redisTemplate.opsForHash().entries(key);
    }*/

    @Override
    public boolean hasKey(Object key) {
        return this.redisTemplate.opsForValue().get(key) != null;
    }

    /**
     * 获取符合条件的key
     *
     * @param pattern 表达式
     * @return
     */
    @Override
    public List<String> keys(String pattern) {
        List<String> keys = new ArrayList<>();
        this.scan(pattern, item -> {
            //符合条件的key
            //String key = new String(item, StandardCharsets.UTF_8);
            String key = new String(item);
            keys.add(key);
        });
        return keys;
    }


    /**
     * scan 实现
     *
     * @param pattern  表达式
     * @param consumer 对迭代到的key进行操作
     */
    private void scan(String pattern, Consumer<byte[]> consumer) {
        this.redisTemplate.execute((RedisConnection connection) -> {
            try (Cursor<byte[]> cursor =
                         connection.scan(ScanOptions.scanOptions()
                                 .count(Long.MAX_VALUE)
                                 .match(pattern).build())) {
                cursor.forEachRemaining(consumer);
                return null;

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }


    /*@Override
    public Long cumulative(Object key, Object value) {
        HyperLogLogOperations<Object, Object> operations = redisTemplate.opsForHyperLogLog();
        // add 方法对应 PFADD 命令
        return operations.add(key, value);

    }*/

    /*@Override
    public Long counter(Object key) {
        HyperLogLogOperations<Object, Object> operations = redisTemplate.opsForHyperLogLog();

        // add 方法对应 PFADD 命令
        return operations.size(key);
    }*/

    /*@Override
    public List multiCounter(Collection keys) {
        if (keys == null) {
            return new ArrayList();
        }
        List<Long> result = new ArrayList<>();
        for (Object key : keys) {
            result.add(counter(key));
        }
        return result;
    }*/

    /*@Override
    public Long mergeCounter(Object... key) {
        HyperLogLogOperations<Object, Object> operations = redisTemplate.opsForHyperLogLog();
        // 计数器合并累加
        return operations.union(key[0], key);
    }*/

    /*@Override
    public Long incr(String key, long liveTime) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();

        if ((null == increment || increment.longValue() == 0) && liveTime > 0) {//初始设置过期时间
            entityIdCounter.expire(liveTime, TimeUnit.SECONDS);
        }

        return increment;
    }*/

    /**
     * 使用Sorted Set记录keyword
     * zincrby命令，对于一个Sorted Set，存在的就把分数加x(x可自行设定)，不存在就创建一个分数为1的成员
     *
     * @param sortedSetName sortedSetName的Sorted Set不用预先创建，不存在会自动创建，存在则向里添加数据
     * @param keyword       关键词
     */
    /*@Override
    public void incrementScore(String sortedSetName, String keyword) {
        // x 的含义请见本方法的注释
        double x = 1.0;
        this.redisTemplate.opsForZSet().incrementScore(sortedSetName, keyword, x);
    }*/


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        redisCache = this;
    }
}
