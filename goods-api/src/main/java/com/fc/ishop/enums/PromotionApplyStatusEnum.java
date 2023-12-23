package com.fc.ishop.enums;

/**
 * 促销活动申请状态枚举
 * @author florence
 * @date 2023/12/20
 */
public enum PromotionApplyStatusEnum {
    APPLY("申请"), PASS("通过"), REFUSE("拒绝");

    private final String description;

    PromotionApplyStatusEnum(String str) {
        this.description = str;
    }

    public String description() {
        return description;
    }
}
