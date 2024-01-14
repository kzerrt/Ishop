package com.fc.ishop.service;

/**
 * 短信发送服务
 * @author florence
 * @date 2024/1/4
 */
public interface SmsService {
    /**
     * 验证码发送
     * @param mobile
     * @param type
     * @param uuid
     */
    boolean sendSmsCode(String mobile, String type, String uuid);
}
