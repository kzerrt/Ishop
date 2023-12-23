package com.fc.ishop.enums;

/**
 * @author florence
 * @date 2023/12/18
 */
public enum PromotionStatusEnum {
    NEW("新建"), START("开始/上架"), END("结束/下架"), CLOSE("紧急关闭/作废");

    private final String description;

    PromotionStatusEnum(String str) {
        this.description = str;
    }

    public String description() {
        return description;
    }
}
