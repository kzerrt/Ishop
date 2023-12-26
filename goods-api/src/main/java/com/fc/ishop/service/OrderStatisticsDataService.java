package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.StoreFlow;
import com.fc.ishop.vo.StatisticsQueryParam;
import com.fc.ishop.vo.order.OrderOverviewVo;
import com.fc.ishop.vo.order.OrderStatisticsDataVo;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/26
 */
public interface OrderStatisticsDataService extends IService<StoreFlow> {
    OrderOverviewVo overview(StatisticsQueryParam statisticsQueryParam);

    List<OrderStatisticsDataVo> statisticsChart(StatisticsQueryParam statisticsQueryParam);
}
