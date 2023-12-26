package com.fc.ishop.vo;

import lombok.Data;

/**
 * 统计查询参数
 * @author florence
 * @date 2023/12/25
 */
@Data
public class StatisticsQueryParam {
    //(value = "快捷搜索", allowableValues = "TODAY, YESTERDAY, LAST_SEVEN, LAST_THIRTY")
    private String searchType;

    //(value = "类型：年（YEAR）、月（MONTH）")
    private String timeType;

    //(value = "年份")
    private Integer year;

    //(value = "月份")
    private Integer month;

    //(value = "店铺ID")
    private String storeId;
}
