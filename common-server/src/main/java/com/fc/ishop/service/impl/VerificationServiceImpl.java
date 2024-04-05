package com.fc.ishop.service.impl;

import com.fc.ishop.cache.Cache;
import com.fc.ishop.cache.CachePrefix;
import com.fc.ishop.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author florence
 * @date 2024/1/4
 */
@Service
public class VerificationServiceImpl implements VerificationService {
    @Autowired
    @Qualifier("redisCache")
    private Cache cache;
    @Override
    public boolean check(String uuid, String type) {
        // todo 未使用到
        Object o = cache.get(cacheResult(type, uuid));
        if (o == null) {
            return false;
        } else {
            cache.remove(cacheResult(type, uuid));
            return true;
        }
    }

    /**
     * 生成缓存key 记录缓存验证的结果
     *
     * @param type              验证码类型
     * @param uuid              用户uuid
     * @return 缓存key
     */
    public static String cacheResult(String type, String uuid) {
        return CachePrefix.VERIFICATION_RESULT.getPrefix() + type + uuid;
    }
}
