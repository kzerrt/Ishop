package com.fc.ishop.enums;

/**
 * 促销分类枚举
 */
public enum PromotionTypeEnum {
    /**
     * 促销枚举
     */
    PINTUAN("拼团"), SECKILL("秒杀"), COUPON("优惠券"), FULL_DISCOUNT("满减"), POINTS_GOODS("积分商品");


    private final String description;

    PromotionTypeEnum(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
