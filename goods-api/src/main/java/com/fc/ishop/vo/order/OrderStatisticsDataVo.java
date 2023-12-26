package com.fc.ishop.vo.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author florence
 * @date 2023/12/26
 */
@Data
public class OrderStatisticsDataVo {
    //(value = "店铺")
    private String storeName;

    //(value = "购买人")
    private String memberName;

    //(value = "订单金额")
    private Double price;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //(value = "创建时间")
    private Date createTime;

    //(value = "订单编号")
    private String orderItemSn;
}
