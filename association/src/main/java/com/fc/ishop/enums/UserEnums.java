package com.fc.ishop.enums;

/**
 * token角色信息
 */
public enum UserEnums {
    /**
     * 角色
     */
    MEMBER("会员"),
    STORE("商家"),
    MANAGER("管理员"),
    SYSTEM("系统");
    private final String role;

    UserEnums(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
