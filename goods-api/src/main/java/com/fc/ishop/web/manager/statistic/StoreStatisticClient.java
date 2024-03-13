package com.fc.ishop.web.manager.statistic;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author florence
 * @date 2023/12/24
 */
@Deprecated
@FeignClient(value = "goods-server", contextId = "storeStatisticClient")
public interface StoreStatisticClient {
    /**
     * 获取订单数量
     * @return
     */
    @GetMapping("/storeStatistic/storeNum")
    Integer storeNum();

    /**
     * 今日新增店铺数
     * @return
     */
    @GetMapping("/storeStatistic/today")
    Integer todayStoreNum();

}
