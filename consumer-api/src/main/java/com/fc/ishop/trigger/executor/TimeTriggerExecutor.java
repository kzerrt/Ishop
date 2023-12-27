package com.fc.ishop.trigger.executor;

/**
 * 延迟任务执行器
 * @author florence
 * @date 2023/12/27
 */
public interface TimeTriggerExecutor {
    /**
     * 执行任务
     * @param object 任务参数
     */
    void execute(Object object);
}
