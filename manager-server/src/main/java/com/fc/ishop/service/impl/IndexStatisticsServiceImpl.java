package com.fc.ishop.service.impl;

import com.fc.ishop.constant.IndexConstant;
import com.fc.ishop.enums.GoodsAuthEnum;
import com.fc.ishop.enums.GoodsStatusEnum;
import com.fc.ishop.enums.OrderStatusEnum;
import com.fc.ishop.service.statistic.IndexStatisticsService;
import com.fc.ishop.service.statistic.MemberStatisticsDataService;
import com.fc.ishop.service.statistic.PlatformService;
import com.fc.ishop.vo.IndexStatisticsVo;
import com.fc.ishop.web.manager.statistic.GoodsStatisticClient;
import com.fc.ishop.web.manager.statistic.IndexStatisticClient;
import com.fc.ishop.web.manager.statistic.OrderStatisticClient;
import com.fc.ishop.web.manager.statistic.StoreStatisticClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author florence
 * @date 2023/12/24
 */
@Service("indexStatisticsService")
public class IndexStatisticsServiceImpl implements IndexStatisticsService {

    @Autowired
    private IndexStatisticClient indexStatisticClient;

    /**
     * 会员统计
     */
    @Autowired
    private MemberStatisticsDataService memberStatisticsDataService;


    /**
     * 平台pv统计
     */
    @Autowired
    private PlatformService platformService;

    @Override
    public IndexStatisticsVo indexStatistics() {
        //首页统计内容
        IndexStatisticsVo vo = new IndexStatisticsVo();
        Map<String, Object> info = indexStatisticClient.indexStatisticsInfo();
        // 统计订单
        vo.setOrderNum((Integer) info.get(IndexConstant.orderNum));
        // 获取总会员数量
        vo.setMemberNum(memberStatisticsDataService.getMemberCount());
        // 获取上架商品数量
        vo.setGoodsNum((Integer) info.get(IndexConstant.goodsNum));
        //获取总店铺数量
        //vo.setStoreNum(storeStatisticClient.storeNum());


        // 今日订单数
        vo.setTodayOrderNum((Integer) info.get(IndexConstant.todayOrderNum));
        // 今日交易额
        vo.setTodayOrderPrice((Double) info.get(IndexConstant.orderPrice));
        // 今日新增会员数量
        vo.setTodayMemberNum(memberStatisticsDataService.todayMemberNum());
        // 今日新增商品数量
        vo.setTodayGoodsNum((Integer) info.get(IndexConstant.todayGoods));
        // 进入新增店铺数量
        //vo.setTodayStoreNum(storeStatisticClient.todayStoreNum());
        // 当前在线人数
        vo.setCurrentNumberPeopleOnline(platformService.online());
        return vo;
    }
}
