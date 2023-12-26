package com.fc.ishop.service.impl;

import com.fc.ishop.enums.GoodsAuthEnum;
import com.fc.ishop.enums.GoodsStatusEnum;
import com.fc.ishop.service.statistic.IndexStatisticsService;
import com.fc.ishop.service.statistic.MemberStatisticsDataService;
import com.fc.ishop.service.statistic.PlatformService;
import com.fc.ishop.vo.IndexStatisticsVo;
import com.fc.ishop.web.manager.statistic.GoodsStatisticClient;
import com.fc.ishop.web.manager.statistic.OrderStatisticClient;
import com.fc.ishop.web.manager.statistic.StoreStatisticClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author florence
 * @date 2023/12/24
 */
@Service("indexStatisticsService")
public class IndexStatisticsServiceImpl implements IndexStatisticsService {
    /**
     * 订单统计
     */
    @Autowired
    private OrderStatisticClient orderStatisticClient;

    /**
     * 会员统计
     */
    @Autowired
    private MemberStatisticsDataService memberStatisticsDataService;

    /**
     * 商品统计
     */
    @Autowired
    private GoodsStatisticClient goodsStatisticClient;

    /**
     * 店铺统计
     */
    @Autowired
    private StoreStatisticClient storeStatisticClient;

    /**
     * 平台pv统计
     */
    @Autowired
    private PlatformService platformService;

    @Override
    public IndexStatisticsVo indexStatistics() {
        //首页统计内容
        IndexStatisticsVo vo = new IndexStatisticsVo();

        // 统计订单
        vo.setOrderNum(orderStatisticClient.orderNum(""));
        // 获取总会员数量
        vo.setMemberNum(memberStatisticsDataService.getMemberCount());
        // 获取上架商品数量
        vo.setGoodsNum(goodsStatisticClient.goodsNum(GoodsStatusEnum.UPPER.name(), GoodsAuthEnum.PASS.name()));
        //获取总店铺数量
        vo.setStoreNum(storeStatisticClient.storeNum());


        // 今日新增会员数量
        vo.setTodayMemberNum(memberStatisticsDataService.todayMemberNum());
        // 今日新增商品数量
        vo.setTodayGoodsNum(goodsStatisticClient.todayUpperNum());
        // 进入新增店铺数量
        vo.setTodayStoreNum(storeStatisticClient.todayStoreNum());
        // 当前在线人数
        vo.setCurrentNumberPeopleOnline(platformService.online());
        return vo;
    }
}
