package com.fc.ishop.token.generate;

import com.fc.ishop.enums.UserEnums;
import com.fc.ishop.token.Token;

/**
 * 抽象token生成，定义生成token类
 */
public abstract class AbstractTokenGenerate {
    /**
     * 生成token
     *
     * @param username 用户名
     * @param longTerm 是否长时间有效
     * @return
     */
    public abstract Token createToken(String username, Boolean longTerm);
    /**
     * 生成token
     *
     * @param user 用户类
     * @param longTerm 是否长时间有效
     * @return
     */
    public abstract Token createToken(Object user, Boolean longTerm);
    /**
     * 刷新token
     *
     * @param refreshToken 刷新token
     * @return token
     */
    public abstract Token refreshToken(String refreshToken);

    /**
     * 默认role
     */
    public UserEnums role = UserEnums.MANAGER;
}
