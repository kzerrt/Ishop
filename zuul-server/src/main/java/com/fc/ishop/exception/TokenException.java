package com.fc.ishop.exception;

/**
 * @author florence
 * @date 2023/12/3
 */
public class TokenException extends RuntimeException {
    public TokenException() {
    }

    public TokenException(String message) {
        super(message);
    }
}
