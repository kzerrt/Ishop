package com.fc.ishop.enums;

/**
 * @author florence
 * @date 2023/12/18
 */
public enum DeliverStatusEnum {
    /**
     * 发货状态
     */
    UNDELIVERED("未发货"),
    DELIVERED("已发货"),
    RECEIVED("已收货");


    private final String description;

    DeliverStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
