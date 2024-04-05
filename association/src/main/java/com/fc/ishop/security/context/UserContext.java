package com.fc.ishop.security.context;

import com.fc.ishop.cache.CachePrefix;
import com.fc.ishop.cache.RedisCache;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.enums.UserEnums;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.security.AuthUser;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 用户上下文获取用户信息
 */
@Slf4j
public class UserContext {
    // 存储用户信息
    private static final AuthenticationHandler authenticationHandler = new AuthenticationHandler();
    /**
     * 通过token获取用户信息(own)
     * @param accessToken
     * @return
     */
    public static AuthUser getCurrentUser(String accessToken) {
        if (accessToken == null) {
            throw new ServiceException(ResultCode.USER_AUTHORITY_ERROR);
        }
        RedisCache cache = RedisCache.getCache();
        Object o = cache.get(CachePrefix.ACCESS_TOKEN.getPrefix(UserEnums.MANAGER) + accessToken);
        if (!(o instanceof String)) {
            throw new ServiceException(ResultCode.USER_NOT_LOGIN);
        }
        AuthUser authUser;
        try {
            authUser = new Gson().fromJson((String) o, AuthUser.class);
        } catch (Exception e) {
            throw new ServiceException(ResultCode.USER_NOT_LOGIN);
        }
        if (authUser == null) {
            throw new ServiceException(ResultCode.USER_NOT_LOGIN);
        }
        return authUser;
    }

    /**
     * 在所有缓存中查询带有该token的用户
     * @param accessToken
     * @return
     */
    public static AuthUser getCurrentInAll(String accessToken) {
        String user = getCurrentUserToStr(accessToken);
        return new Gson().fromJson(user, AuthUser.class);
    }
    public static String getCurrentUserToStr(String accessToken) {
        if (accessToken == null) {
            throw new ServiceException("用户请求token为空");
        }
        RedisCache cache = RedisCache.getCache();
        // 通过正则匹配key
        List<String> keys = cache.keys("*" + accessToken);
        if (keys.size() == 0) {
            throw new ServiceException(ResultCode.USER_AUTHORITY_ERROR);
        }
        // 不管获取到多少用户，只取第一个�� t ;{ACCESS_TOKEN_MANAGER}_3c79655b-df85-3eb2-b61c-38b6a3ec7a0d
        //String user = keys.get(0);
        int indexOf = keys.get(0).indexOf("{");
        String user = cache.getString(keys.get(0));
        if (user == null) {
            log.debug("token:{}, 未获取到用户 key {},\n 截取字符串为 {}",
                    accessToken, keys.get(0), keys.get(0).substring(indexOf));
        }
        return user;
    }

    public static AuthUser getCurrentUser() {
        return authenticationHandler.getAuthUser();
    }
    public static void setCurrentUser(AuthUser authUser) {
        authenticationHandler.setAuthUser(authUser);
    }
    public static void removeCurrentUser() {
        authenticationHandler.remove();
    }
}
