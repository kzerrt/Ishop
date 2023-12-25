package com.fc.ishop.dos.trade;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fc.ishop.base.BaseEntity;
import com.fc.ishop.dos.goods.GoodsSku;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 促销商品
 * @author florence
 * @date 2023/12/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("li_promotion_goods")
@NoArgsConstructor
public class PromotionGoods extends BaseEntity {

    private static final long serialVersionUID = 4150737500248136108L;

    //@ApiModelProperty(value = "商家ID")
    private String storeId;

    //@ApiModelProperty(value = "商家名称")
    private String storeName;

    //@ApiModelProperty(value = "货品id")
    private String skuId;

    //@ApiModelProperty(value = "货品名称")
    private String goodsName;

    //@ApiModelProperty(value = "缩略图")
    private String thumbnail;

    //@ApiModelProperty(value = "活动开始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MMM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    //@ApiModelProperty(value = "活动结束时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MMM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    //@ApiModelProperty(value = "活动id")
    private String promotionId;

    /**
     *  cn.lili.modules.promotion.entity.enums.PromotionTypeEnum
     */
    //@ApiModelProperty(value = "促销工具类型")
    private String promotionType;

    //@ApiModelProperty(value = "活动标题")
    private String title;

    //@ApiModelProperty(value = "卖出的商品数量")
    private Integer num;

    //@ApiModelProperty(value = "促销价格")
    private Double price;

    //@ApiModelProperty(value = "限购数量")
    private Integer limitNum;

    //@ApiModelProperty(value = "促销库存")
    private Integer quantity;

    /**
     *  PromotionStatusEnum
     */
    //@ApiModelProperty(value = "状态")
    private String promotionStatus;

    //@ApiModelProperty(value = "分类path")
    private String categoryPath;

    public PromotionGoods(GoodsSku sku) {
        if (sku != null) {
            String oldId = this.getId();
            BeanUtil.copyProperties(sku, this);
            this.setSkuId(sku.getId());
            this.setId(oldId);
        }
    }


}
