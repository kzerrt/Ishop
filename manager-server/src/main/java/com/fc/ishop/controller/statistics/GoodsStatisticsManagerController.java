package com.fc.ishop.controller.statistics;

import com.fc.ishop.dto.GoodsStatisticsQueryParam;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.goods.GoodsStatisticsDataVo;
import com.fc.ishop.web.manager.statistic.GoodsStatisticClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 管理端,商品统计接口
 * @author florence
 * @date 2023/12/26
 */
@RestController
@RequestMapping("/statistics/goods")
public class GoodsStatisticsManagerController {
    @Autowired
    private GoodsStatisticClient goodsStatisticClient;
    //(value = "获取统计列表,排行前一百的数据")
    @GetMapping
    public ResultMessage<List<GoodsStatisticsDataVo>> getByPage(GoodsStatisticsQueryParam goodsStatisticsQueryParam) {
        return goodsStatisticClient.getByPage(goodsStatisticsQueryParam);
    }
}
