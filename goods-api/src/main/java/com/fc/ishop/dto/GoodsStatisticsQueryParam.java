package com.fc.ishop.dto;

import com.fc.ishop.vo.StatisticsQueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品统计
 * @author florence
 * @date 2023/12/26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsStatisticsQueryParam extends StatisticsQueryParam {
    //(value = "查询类型：按数量（NUM）、按金额（PRICE）")
    private String type;
}
