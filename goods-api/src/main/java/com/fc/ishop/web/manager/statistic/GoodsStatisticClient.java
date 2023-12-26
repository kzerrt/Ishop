package com.fc.ishop.web.manager.statistic;

import com.fc.ishop.dto.GoodsStatisticsQueryParam;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.goods.GoodsStatisticsDataVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/24
 */
@FeignClient(value = "goods-server", contextId = "goodsStatisticClient")
public interface GoodsStatisticClient {
    /**
     * 获取订单数量
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

    /**
     * 获取统计列表,排行前一百的数据
     */
    @PostMapping("/goodsStatistic/goods/getByPage")
    ResultMessage<List<GoodsStatisticsDataVo>> getByPage(@RequestBody GoodsStatisticsQueryParam goodsStatisticsQueryParam);

}
