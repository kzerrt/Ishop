package com.fc.ishop.prop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author florence
 * @date 2023/12/19
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ConfigurationProperties(prefix = "ishop.rocketmq")
public class RocketMQCustomProperties {
    private String promotionTopic;

    private String promotionGroup;

    private String orderTopic;

    private String orderGroup;

    private String msgExtTopic;

    private String msgExtGroup;

    private String goodsTopic;

    private String goodsGroup;

    private String topicUser;

    private String memberTopic;

    private String memberGroup;

    private String otherTopic;

    private String otherGroup;

    private String noticeTopic;

    private String noticeGroup;

    private String noticeSendTopic;

    private String noticeSendGroup;

    private String storeTopic;

    private String storeGroup;

    private String afterSaleTopic;

    private String afterSaleGroup;
}
