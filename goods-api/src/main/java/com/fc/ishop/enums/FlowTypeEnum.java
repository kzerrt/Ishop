package com.fc.ishop.enums;

/**
 * 流水枚举
 * @author florence
 * @date 2023/12/26
 */
public enum FlowTypeEnum {
    /**
     * 流水类型
     */
    PAY("支付"),
    REFUND("退款");

    private final String description;

    FlowTypeEnum(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
