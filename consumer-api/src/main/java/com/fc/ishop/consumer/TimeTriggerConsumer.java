package com.fc.ishop.consumer;

import cn.hutool.json.JSONUtil;
import com.fc.ishop.cache.Cache;
import com.fc.ishop.trigger.TimeTriggerMsg;
import com.fc.ishop.trigger.TimeTriggerUtil;
import com.fc.ishop.trigger.executor.PromotionTimeTriggerExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 促销消息消费者
 * @author florence
 * @date 2023/12/27
 */
@Component
@Slf4j
@RocketMQMessageListener(topic = "${ishop.rocketmq.promotion-topic}", consumerGroup = "${ishop.rocketmq.promotion-group}")
public class TimeTriggerConsumer implements RocketMQListener<TimeTriggerMsg> {
    @Autowired
    @Qualifier("redisObj")
    private Cache<Integer> cache;
    @Autowired
    private PromotionTimeTriggerExecutor executor;
    @Override
    public void onMessage(TimeTriggerMsg timeTriggerMsg) {
        String key = TimeTriggerUtil.generateKey(timeTriggerMsg.getTriggerExecutor(), timeTriggerMsg.getTriggerTime(), timeTriggerMsg.getUniqueKey());

        try {
            if (cache.get(key) == null) {
                log.info("执行器执行被取消：{} | 任务标识：{}", timeTriggerMsg.getTriggerExecutor(), timeTriggerMsg.getUniqueKey());
                return;
            }
            log.info("执行器执行：" + timeTriggerMsg.getTriggerExecutor());
            log.info("执行器参数：" + JSONUtil.toJsonStr(timeTriggerMsg.getParam()));

            cache.remove(key);

            executor.execute(timeTriggerMsg.getParam());
        } catch (Exception e) {
            log.error("mq延时任务异常", e);
        }
    }
}
