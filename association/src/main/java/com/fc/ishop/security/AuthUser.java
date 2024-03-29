package com.fc.ishop.security;

import com.fc.ishop.enums.UserEnums;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户授权信息
 */
@Data
@AllArgsConstructor
public class AuthUser implements Serializable {
    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * id
     */
    private String id;

    /**
     * 长期有效（用于手机app登录场景或者信任场景等）(not use)
     */
    private Boolean longTerm = false;

    /**
     * @see UserEnums
     * 角色
     */
    private UserEnums role;

    /**
     * 如果角色是商家，则存在此店铺id字段
     * storeId
     */
    private String storeId;

    /**
     * 如果角色是商家，则存在此店铺名称字段
     * storeName
     */
    private String storeName;

    /**
     * 是否是超级管理员
     */
    private Boolean isSuper = false;

    public AuthUser(String username, String id, String nickName, UserEnums role) {
        this.username = username;
        this.id = id;
        this.role = role;
        this.nickName = nickName;
    }

    public AuthUser(String username, String id, UserEnums manager, String nickName, Boolean isSuper) {
        this.username = username;
        this.id = id;
        this.role = manager;
        this.isSuper = isSuper;
        this.nickName = nickName;
    }
}
