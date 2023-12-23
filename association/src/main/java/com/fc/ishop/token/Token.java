package com.fc.ishop.token;

import lombok.Data;

@Data
public class Token {
    /**
     * 访问token
     */
    private String accessToken;

    /**
     * 刷新token
     */
    private String refreshToken;
}
