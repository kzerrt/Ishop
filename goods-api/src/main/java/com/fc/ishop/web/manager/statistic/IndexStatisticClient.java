package com.fc.ishop.web.manager.statistic;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @author florence
 * @date 2024/3/2
 */
@FeignClient(value = "goods-server", contextId = "indexStatisticClient")
public interface IndexStatisticClient {
    @GetMapping("/indexStatistic/info")
    Map<String, Object> indexStatisticsInfo();
}
