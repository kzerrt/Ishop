package com.fc.ishop.dto;

import com.fc.ishop.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author florence
 * @date 2023/12/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessage {

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 新状态
     */
    private OrderStatusEnum newStatus;
}
