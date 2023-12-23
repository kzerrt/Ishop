package com.fc.ishop.enums;

/**
 * @author florence
 * @date 2023/12/18
 */
public enum DeliveryMethodEnum {
    SELF_PICK_UP("自提"),
    LOCAL_TOWN_DELIVERY("同城配送"),
    LOGISTICS("物流");

    private final String description;

    DeliveryMethodEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
