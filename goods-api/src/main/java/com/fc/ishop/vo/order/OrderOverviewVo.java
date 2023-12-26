package com.fc.ishop.vo.order;

import lombok.Data;

/**
 * 订单统计
 * @author florence
 * @date 2023/12/26
 */
@Data
public class OrderOverviewVo {
    //(value = "UV人次")
    private Integer uvNum;

    //下单统计
    //(value = "下单数量")
    private Long orderNum;

    //(value = "下单人数")
    private Long orderMemberNum;

    //(value = "下单金额")
    private Double orderAmount;

    //付款统计
    //(value = "付款订单数量")
    private Long paymentOrderNum;

    //(value = "付款人数")
    private Long paymentsNum;

    //(value = "付款金额")
    private Double paymentAmount;


    //退单统计
    //(value = "退单笔数")
    private Long refundOrderNum;

    //(value = "退单金额")
    private Double refundOrderPrice;

    // 转换率
    //(value = "下单转换率")
    private String orderConversionRate;

    //(value = "付款转换率")
    private String paymentsConversionRate;

    //(value = "整体转换率")
    private String overallConversionRate;

}
