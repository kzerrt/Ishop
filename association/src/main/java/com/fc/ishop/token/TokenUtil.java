package com.fc.ishop.token;


import com.fc.ishop.cache.Cache;
import com.fc.ishop.cache.CachePrefix;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.enums.UserEnums;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.security.AuthUser;
import com.fc.ishop.security.context.UserContext;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * TokenUtil
 * token生成工具类简化生成
 */
@Component
public class TokenUtil {

    private long tokenExpireTime = 20;
    //private RedisCache cache;
    @Autowired
    private Cache cache;

    /*@PostConstruct
    private void getCache() {
        cache = RedisCache.getCache();
    }*/

    public Token createToken(String username, Object claim, UserEnums userEnums) {
        Token token = new Token();
        // JWT 生成
        /*String accessToken = Jwts.builder()
                // jwt 私有声明
                .claim("userContext", new Gson().toJson(claim))
                // JWT的主体
                .setSubject(username)
                // 失效时间 当前时间+过期分钟
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                // 签名算法和密钥
                .signWith(generalKey())
                .compact();
        token.setRefreshToken(accessToken);
        token.setAccessToken(accessToken);*/
        return token;
    }

    /**
     * 构建token
     * (own)
     *
     * @param username 主体
     * @return
     */
    public Token createToken(String username, UserEnums userEnums) {
        Token token = new Token();
        //访问token
        String accessToken = UUID.randomUUID().toString();
        cache.put(CachePrefix.ACCESS_TOKEN.getPrefix(userEnums) + accessToken, 1,
                tokenExpireTime, TimeUnit.MINUTES);
        token.setAccessToken(accessToken);
        token.setRefreshToken(accessToken);
        return token;
    }

    /**
     * (own)
     *
     * @param user
     * @return
     */
    public Token createToken(AuthUser user) {
        Token token = new Token();
        String accessToken = UUID.nameUUIDFromBytes(user.getId().getBytes(StandardCharsets.UTF_8)).toString();
        cache.put(CachePrefix.ACCESS_TOKEN.getPrefix(user.getRole()) + accessToken,
                new Gson().toJson(user), tokenExpireTime, TimeUnit.MINUTES);
        token.setAccessToken(accessToken);
        token.setRefreshToken(accessToken);
        return token;
    }

    /**
     * 刷新token
     *
     * @param oldRefreshToken 刷新token
     * @return token
     */
    public Token refreshToken(String oldRefreshToken, UserEnums userEnums) {
        AuthUser currentUser = UserContext.getCurrentUser();
        // 判断用户是否为空
        if (currentUser == null || cache.hasKey(CachePrefix.ACCESS_TOKEN.getPrefix(userEnums) + oldRefreshToken)) {
            throw new ServiceException(ResultCode.USER_AUTH_EXPIRED);
        }
        Token token = new Token();
        String refreshToken = UUID.nameUUIDFromBytes(oldRefreshToken.getBytes(StandardCharsets.UTF_8)).toString();
        // 将新token放入
        cache.put(CachePrefix.ACCESS_TOKEN.getPrefix(userEnums) + refreshToken,
                new Gson().toJson(currentUser), 2 * tokenExpireTime, TimeUnit.MINUTES);
        token.setAccessToken(refreshToken);
        token.setRefreshToken(refreshToken);
        // 移除旧token
        cache.remove(CachePrefix.ACCESS_TOKEN.getPrefix(userEnums) + oldRefreshToken);
        return token;
    }

    private static SecretKey generalKey() {
        /*byte[] encodedKey = Base64.decodeBase64("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=");//自定义
        javax.crypto.SecretKey key = Keys.hmacShaKeyFor(encodedKey);*/
        return null;
    }

}
