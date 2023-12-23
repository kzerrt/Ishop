package com.fc.ishop.trigger;

import cn.hutool.json.JSONUtil;
import com.fc.ishop.delayqueue.AbstractDelayQueueMachineFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 促销延迟队列
 * @author florence
 * @date 2023/12/20
 */
@Component
public class PromotionDelayQueue extends AbstractDelayQueueMachineFactory {

    @Autowired
    private TimeTrigger timeTrigger;// rocketmqTimerTrigger

    /**
     * 执行延时任务
     * @param jobId 任务id(TimerTriggerMsg)
     */
    @Override
    public void invoke(String jobId) {
        timeTrigger.add(JSONUtil.toBean(jobId, TimeTriggerMsg.class));
    }

    @Override
    public String setDelayQueueName() {
        return "promotion_delay";
    }
}
