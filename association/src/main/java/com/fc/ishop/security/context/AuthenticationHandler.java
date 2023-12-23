package com.fc.ishop.security.context;

import com.fc.ishop.security.AuthUser;

public class AuthenticationHandler {
    // 用于存放用户信息
    private final ThreadLocal<AuthUser> USER_LOCAL = new ThreadLocal<>();
    public void setAuthUser(AuthUser authUser) {
        if (authUser == null) {
            return;
        }
        USER_LOCAL.set(authUser);
    }
    /**
     * 获取当前用户信息
     *
     * @return
     */
    public AuthUser getAuthUser() {

        AuthUser authUser = USER_LOCAL.get();
        if (authUser == null) {
            return null;
        }
        return authUser;
    }
    public void remove() {
        USER_LOCAL.remove();
    }
}
