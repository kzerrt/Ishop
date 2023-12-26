package com.fc.ishop.service;

import com.fc.ishop.dto.GoodsStatisticsQueryParam;
import com.fc.ishop.vo.goods.GoodsStatisticsDataVo;

import java.util.List;

/**
 * 商品统计
 * @author florence
 * @date 2023/12/26
 */
public interface GoodsStatisticsDataService {
    List<GoodsStatisticsDataVo> getGoodsStatisticsData(GoodsStatisticsQueryParam goodsStatisticsQueryParam, int i);
}
