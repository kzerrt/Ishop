package com.fc.ishop.security.enums;

/**
 * @author florence
 * @date 2023/11/22
 */
public enum SecurityEnum {
    /**
     * 参与在header中的参数名
     */
    HEADER_TOKEN("accessToken"),
    USER_CONTEXT("userContext"),// 存储用户gson串
    AUTHENTICATED("authenticated");// 认证后发送header给微服务
    String value;

    SecurityEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
