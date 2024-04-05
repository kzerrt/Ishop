package com.fc.ishop.service.impl;

import com.fc.ishop.cache.Cache;
import com.fc.ishop.cache.CachePrefix;
import com.fc.ishop.service.SmsService;
import com.fc.ishop.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author florence
 * @date 2024/1/4
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {
    @Autowired
    @Qualifier("redisCache")
    private Cache<String> cache;
    @Override
    public boolean sendSmsCode(String mobile, String type, String uuid) {
        // todo 使用阿里云短信服务发送验证码
        // 验证码
        String code = StringUtils.getRandStr(6);
        log.info("用户手机号发送的验证码为： {}", code);
        //缓存中写入要验证的信息
        cache.put(cacheKey(type, mobile, uuid), code, 300L);
        return true;
    }

    /**
     * 创建
     * @param type
     * @param mobile
     * @param uuid
     * @return
     */
    private String cacheKey(String type, String mobile, String uuid) {
        return CachePrefix.SMS_CODE.getPrefix() + type + mobile;
    }
}
