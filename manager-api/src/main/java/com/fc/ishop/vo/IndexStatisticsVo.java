package com.fc.ishop.vo;

import lombok.Data;

/**
 * 首页统计内容
 * @author florence
 * @date 2023/12/24
 */
@Data
public class IndexStatisticsVo {
    //(value = "订单总数量")
    private Integer orderNum;
    //(value = "商品总数量")
    private Integer goodsNum;
    //(value = "会员总数量")
    private Integer memberNum;
    //(value = "店铺总数量")
    private Integer storeNum;

    /**
     * 流量概括
     */
    //(value = "今日访问数UV")
    private Integer todayUV;
    //(value = "昨日访问数UV")
    private Integer yesterdayUV;
    //(value = "前七日访问数UV")
    private Integer lastSevenUV;
    //(value = "三十日访问数UV")
    private Integer lastThirtyUV;

    /**
     * 今日信息概括
     */
    //(value = "今日订单数")
    private Long todayOrderNum;
    //(value = "今日下单金额")
    private Double todayOrderPrice;
    //(value = "今日新增会员数量")
    private Integer todayMemberNum;
    //(value = "今日新增商品数量")
    private Integer todayGoodsNum;
    //(value = "今日新增店铺数量")
    private Integer todayStoreNum;
    //(value = "今日新增评论数量")
    private Integer todayMemberEvaluation;

    //当前在线人数
    //(value = "当前在线人数")
    private Long currentNumberPeopleOnline;
}
