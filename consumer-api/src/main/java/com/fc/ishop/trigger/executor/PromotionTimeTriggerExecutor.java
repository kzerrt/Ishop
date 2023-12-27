package com.fc.ishop.trigger.executor;

import cn.hutool.json.JSONUtil;
import com.fc.ishop.delayqueue.PromotionMessage;
import com.fc.ishop.enums.PromotionStatusEnum;
import com.fc.ishop.prop.RocketMQCustomProperties;
import com.fc.ishop.service.PromotionService;
import com.fc.ishop.trigger.TimeExecuteConstant;
import com.fc.ishop.trigger.TimeTrigger;
import com.fc.ishop.trigger.TimeTriggerMsg;
import com.fc.ishop.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author florence
 * @date 2023/12/27
 */
@Slf4j
@Component
public class PromotionTimeTriggerExecutor implements TimeTriggerExecutor{
    @Autowired
    private TimeTrigger timeTrigger;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private RocketMQCustomProperties rocketMQCustomProperties;
    @Override
    public void execute(Object object) {
        PromotionMessage promotionMessage = JSONUtil.toBean(JSONUtil.parseObj(object), PromotionMessage.class);

        // 促销延迟消息
        if (promotionMessage != null && promotionMessage.getPromotionId() != null) {
            log.info("促销活动信息消费：{}", promotionMessage);
            // 如果为促销活动开始，则需要发布促销活动结束的定时任务
            if (PromotionStatusEnum.START.name().equals(promotionMessage.getPromotionStatus())) {
                if (!promotionService.updatePromotionStatus(promotionMessage)) {
                    log.error("开始促销活动失败: {}", promotionMessage);
                    return;
                }
                // 促销活动开始后，设置促销活动结束的定时任务
                promotionMessage.setPromotionStatus(PromotionStatusEnum.END.name());
                String uniqueKey = "{TIME_TRIGGER_" + promotionMessage.getPromotionType() + "}_" + promotionMessage.getPromotionId();
                // 结束时间（延时一分钟）
                long closeTime = promotionMessage.getEndTime().getTime() + 60000;
                TimeTriggerMsg timeTriggerMsg =
                        new TimeTriggerMsg(TimeExecuteConstant.PROMOTION_EXECUTOR,
                                closeTime, promotionMessage, uniqueKey, rocketMQCustomProperties.getPromotionTopic());
                timeTrigger.addDelay(timeTriggerMsg, DateUtil.getDelayTime(promotionMessage.getEndTime().getTime()));
            } else {
                promotionService.updatePromotionStatus(promotionMessage);
            }
        }
        log.error("促销活动信息错误 {}", object);
    }
}
