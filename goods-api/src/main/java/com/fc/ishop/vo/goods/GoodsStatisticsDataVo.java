package com.fc.ishop.vo.goods;

import lombok.Data;

/**
 * 商品统计vo
 * @author florence
 * @date 2023/12/26
 */
@Data
public class GoodsStatisticsDataVo {
    //(value = "商品ID")
    private String goodsId;

    //(value = "商品名称")
    private String goodsName;

    //(value = "销售数量")
    private String num;

    //(value = "销售金额")
    private Double price;
}
