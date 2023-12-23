package com.fc.ishop.dos.trade;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fc.ishop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 限时抢购申请
 * @author florence
 * @date 2023/12/20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("li_seckill_apply")
public class SecKillApply extends BaseEntity {

    private static final long serialVersionUID = 5440164641970820989L;

    //(value = "活动id", required = true)
    @NotNull(message = "活动id参数不能为空")
    @Min(value = 0, message = "活动id参数异常")
    private String seckillId;

    //(value = "时刻")
    @NotNull(message = "时刻参数不能为空")
    private Integer timeLine;

    //(value = "skuID")
    @NotNull(message = "skuId参数不能为空")
    @Min(value = 0, message = "skuID参数异常")
    private String skuId;

    //(value = "商品名称")
    @NotEmpty(message = "商品名称参数不能为空")
    private String goodsName;

    //(value = "商家id")
    private String storeId;

    //(value = "商家名称")
    private String storeName;

    //(value = "价格")
    @NotNull(message = "价格参数不能为空")
    @Min(value = 0, message = "价格参数不能小于0")
    private Double price;

    //(value = "促销数量")
    @NotNull(message = "促销数量参数不能为空")
    @Min(value = 0, message = "促销数量数不能小于0")
    private Integer quantity;

    /**
     *  PromotionApplyStatusEnum
     */
    //(value = "APPLY(\"申请\"), PASS(\"通过\"), REFUSE(\"拒绝\")")
    private String promotionApplyStatus;

    //(value = "驳回原因")
    private String failReason;

    //(value = "已售数量")
    private Integer salesNum;

    //(value = "商品原始价格")
    private Double originalPrice;
}
