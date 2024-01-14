package com.fc.ishop.service;

/**
 * 验证码接口
 * @author florence
 * @date 2024/1/4
 */
public interface VerificationService {

    /**
     * 检查缓存中是否存在有该id
     * @param uuid
     * @param type
     * @return
     */
    boolean check(String uuid, String type);
}
