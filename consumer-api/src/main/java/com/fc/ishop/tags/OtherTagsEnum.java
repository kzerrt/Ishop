package com.fc.ishop.tags;

/**
 * @author paulG
 * @since 2020/12/9
 **/
public enum OtherTagsEnum {

    MESSAGE("站内消息提醒"),
    SMS("短信消息提醒");

    private final String description;

    OtherTagsEnum(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }


}
