package com.fc.ishop.web.manager.statistic;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author florence
 * @date 2023/12/24
 */
@FeignClient(value = "goods-server", contextId = "orderStatisticClient")
public interface OrderStatisticClient {
    /**
     * 获取订单数量
     * @return
     */
    @GetMapping("/orderStatistic/orderNum")
    Integer orderNum(String orderStatus);
}
