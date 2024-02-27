package com.fc.ishop.tags;

/**
 * @author paulG
 * @since 2020/12/9
 **/
public enum MqOrderTagsEnum {


    ORDER_CREATE("订单创建"),
    STATUS_CHANGE("订单状态改变");




    private final String description;

    MqOrderTagsEnum(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }


}
