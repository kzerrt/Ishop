package com.fc.ishop.web.manager.statistic;

import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.StatisticsQueryParam;
import com.fc.ishop.vo.order.OrderOverviewVo;
import com.fc.ishop.vo.order.OrderStatisticsDataVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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
    @PostMapping("/orderStatistic/orderNum")
    Integer orderNums(@RequestBody String orderStatus);

    /**
     * 订单统计概览
     * @param statisticsQueryParam
     * @return
     */
    @PostMapping("/orderStatistic/overview")
    ResultMessage<OrderOverviewVo> overview(@RequestBody StatisticsQueryParam statisticsQueryParam);

    /**
     * 图表统计订单
     * @param statisticsQueryParam
     * @return
     */
    @PostMapping("/orderStatistic/chart")
    ResultMessage<List<OrderStatisticsDataVo>> statisticsChart(@RequestBody StatisticsQueryParam statisticsQueryParam);
}
