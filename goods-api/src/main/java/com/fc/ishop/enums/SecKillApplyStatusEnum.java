package com.fc.ishop.enums;

/**
 * @author florence
 * @date 2023/12/20
 */
public enum SecKillApplyStatusEnum {
    /**
     * 当前店铺对当前限时抢购的申请状态
     */
    APPLIED("已经申请过"), NOT_APPLY("未报名"), EXPIRE("过期的");

    private final String description;

    SecKillApplyStatusEnum(String str) {
        this.description = str;
    }

    public String description() {
        return description;
    }
}
