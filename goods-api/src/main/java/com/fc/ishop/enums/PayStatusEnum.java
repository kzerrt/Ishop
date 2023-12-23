package com.fc.ishop.enums;

/**
 * @author florence
 * @date 2023/12/18
 */
public enum PayStatusEnum {
    /**
     * 支付状态
     */
    UNPAID("未付款"),
    PAID("已付款");

    private final String description;

    PayStatusEnum(String description) {
        this.description = description;
    }

    public String description() {
        return this.description;
    }
}
