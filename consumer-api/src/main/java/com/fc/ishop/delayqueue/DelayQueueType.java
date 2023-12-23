package com.fc.ishop.delayqueue;

/**
 * 延时任务类型
 *
 * @author paulG
 * @since 2021/5/7
 **/
public enum DelayQueueType {

    /**
     * 促销活动
     */
    PROMOTION("促销活动");

    private final String description;

    DelayQueueType(String des) {
        this.description = des;
    }

    public String description() {
        return this.description;
    }

}
