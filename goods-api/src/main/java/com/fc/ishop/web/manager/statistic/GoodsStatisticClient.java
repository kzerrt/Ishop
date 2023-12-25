package com.fc.ishop.web.manager.statistic;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author florence
 * @date 2023/12/24
 */
@FeignClient(value = "goods-server", contextId = "goodsStatisticClient")
public interface GoodsStatisticClient {
    /**
     * 获取订单数量
     * @return
     */
    @GetMapping("/goodsStatistic/goodsNum")
    Integer goodsNum(@RequestParam("goodsStatusEnum") String goodsStatusEnum,
                     @RequestParam("goodsAuthEnum") String goodsAuthEnum);

    /**
     * 今日新增商品数
     * @return
     */
    @GetMapping("/goodsStatistic/todayUpper")
    Integer todayUpperNum();

}
