package com.fc.ishop.controller.statistics;

import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.StatisticsQueryParam;
import com.fc.ishop.vo.order.OrderOverviewVo;
import com.fc.ishop.vo.order.OrderStatisticsDataVo;
import com.fc.ishop.web.manager.statistic.OrderStatisticClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/26
 */
@RestController
@RequestMapping("statistics/order")
public class OrderStatisticsManagerController {
    @Autowired
    private OrderStatisticClient orderStatisticClient;
    //(value = "订单概览统计")
    @GetMapping("/overview")
    public ResultMessage<OrderOverviewVo> overview(StatisticsQueryParam statisticsQueryParam) {
        return orderStatisticClient.overview(statisticsQueryParam);
    }

    //(value = "订单图表统计")
    @GetMapping
    public ResultMessage<List<OrderStatisticsDataVo>> statisticsChart(StatisticsQueryParam statisticsQueryParam) {
        return orderStatisticClient.statisticsChart(statisticsQueryParam);
    }
}
