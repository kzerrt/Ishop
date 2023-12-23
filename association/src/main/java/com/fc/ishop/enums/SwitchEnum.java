package com.fc.ishop.enums;

/**
 *  开关枚举类
 *  @author florence
 * @date 2023/12/1
 */
public enum SwitchEnum {

    /**
     * 开关
     */
    OPEN("开启"), CLOSE("关闭");

    private String description;

    SwitchEnum(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
