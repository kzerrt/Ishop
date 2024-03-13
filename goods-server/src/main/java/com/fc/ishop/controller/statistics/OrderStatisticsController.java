package com.fc.ishop.controller.statistics;

import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.service.OrderService;
import com.fc.ishop.service.OrderStatisticsDataService;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.StatisticsQueryParam;
import com.fc.ishop.vo.order.OrderOverviewVo;
import com.fc.ishop.vo.order.OrderStatisticsDataVo;
import com.fc.ishop.web.manager.statistic.OrderStatisticClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/26
 */
@RestController
public class OrderStatisticsController implements OrderStatisticClient {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderStatisticsDataService orderStatisticsDataService;
    @Override
    public Integer orderNums(String orderStatus) {
        return orderService.orderNum(orderStatus);
    }

    @Override
    public ResultMessage<OrderOverviewVo> overview(StatisticsQueryParam statisticsQueryParam) {
        return ResultUtil.data(orderStatisticsDataService.overview(statisticsQueryParam));
    }

    @Override
    public ResultMessage<List<OrderStatisticsDataVo>> statisticsChart(StatisticsQueryParam statisticsQueryParam) {
        return ResultUtil.data(orderStatisticsDataService.statisticsChart(statisticsQueryParam));
    }

}
