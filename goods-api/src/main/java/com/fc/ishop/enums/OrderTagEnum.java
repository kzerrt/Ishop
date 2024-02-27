package com.fc.ishop.enums;

/**
 * 前端订单页面TAB标签枚举
 * @author florence
 * @date 2023/12/18
 */
public enum OrderTagEnum {
    /**
     * 所有订单
     */
    ALL("全部"),

    /**
     * 待付款
     */
    WAIT_PAY("待付款"),

    /**
     * 待收货
     */
    WAIT_SHIP("待发货"),

    /**
     * 待收货
     */
    WAIT_ROG("待收货"),

    /**
     * 已完成
     */
    COMPLETE("已完成"),

    /**
     * 已取消
     */
    CANCELLED("已取消");

    private final String description;


    OrderTagEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static OrderTagEnum defaultType() {
        return ALL;
    }
}
