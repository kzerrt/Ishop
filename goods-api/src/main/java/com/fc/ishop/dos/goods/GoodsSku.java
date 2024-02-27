package com.fc.ishop.dos.goods;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fc.ishop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import java.util.Date;

/**
 * 商品sku对象
 * @author florence
 * @date 2023/12/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("i_goods_sku")
public class GoodsSku extends BaseEntity {
    private static final long serialVersionUID = 4865908658161118934L;

    //@ApiModelProperty(value = "商品id")
    private String goodsId;

    //@ApiModelProperty(value = "规格信息json", hidden = true)
    @JsonIgnore
    private String specs;

    //@ApiModelProperty(value = "规格信息")
    private String simpleSpecs;

   // @ApiModelProperty(value = "配送模版id")
    private String freightTemplateId;

    //@ApiModelProperty(value = "是否是促销商品")
    private Boolean isPromotion;

    //@ApiModelProperty(value = "促销价")
    private Double promotionPrice;

    //@ApiModelProperty(value = "商品名称")
    private String goodsName;

    @Length(max = 30, message = "商品规格编号太长，不能超过30个字符")
    //@ApiModelProperty(value = "商品编号")
    private String sn;

    //@ApiModelProperty(value = "品牌id")
    private String brandId;

    //@ApiModelProperty(value = "分类path")
    private String categoryPath;

    //@ApiModelProperty(value = "计量单位")
    private String goodsUnit;

    //@ApiModelProperty(value = "卖点")
    private String sellingPoint;

    //@ApiModelProperty(value = "重量")
    @Max(value = 99999999, message = "重量不能超过99999999")
    private Double weight;
    /**
     *  GoodsStatusEnum
     */
    //@ApiModelProperty(value = "上架状态")
    private String marketEnable;

    //@ApiModelProperty(value = "商品详情")
    //@Column(columnDefinition = "TEXT")
    private String intro;

    @Max(value = 99999999, message = "价格不能超过99999999")
    //@ApiModelProperty(value = "商品价格")
    private Double price;

    @Max(value = 99999999, message = "成本价格99999999")
    //@ApiModelProperty(value = "成本价格")
    private Double cost;

    //@ApiModelProperty(value = "浏览数量")
    private Integer viewCount;

    //@ApiModelProperty(value = "购买数量")
    private Integer buyCount;

    @Max(value = 99999999, message = "库存不能超过99999999")
    //@ApiModelProperty(value = "库存")
    private Integer quantity;

    //@ApiModelProperty(value = "商品好评率")
    private Double grade;

    //@ApiModelProperty(value = "缩略图路径")
    private String thumbnail;

    //@ApiModelProperty(value = "大图路径")
    private String big;

    //@ApiModelProperty(value = "小图路径")
    private String small;

    //@ApiModelProperty(value = "原图路径")
    private String original;

    //@ApiModelProperty(value = "店铺分类id")
    private String storeCategoryPath;

    //@ApiModelProperty(value = "评论数量")
    private Integer commentNum;

    //@ApiModelProperty(value = "卖家id")
    private String storeId;

    //@ApiModelProperty(value = "卖家名字")
    private String storeName;

   // @ApiModelProperty(value = "运费模板id")
    private String templateId;

    //@ApiModelProperty(value = " 谁承担运费 BUYER：买家承担，STORE：卖家承担")
    private String freightPayer;
    /**
     * GoodsAuthEnum
     */
    //@ApiModelProperty(value = "审核状态")
    private String isAuth;

    //@ApiModelProperty(value = "审核信息")
    private String authMessage;

    //@ApiModelProperty(value = "下架原因")
    private String underMessage;

    //@ApiModelProperty(value = "是否自营")
    private Boolean selfOperated;

    //@ApiModelProperty(value = "商品移动端详情")
    //@Column(columnDefinition = "TEXT")
    private String mobileIntro;

    //@ApiModelProperty(value = "商品视频")
    private String goodsVideo;

    //@ApiModelProperty(value = "是否为推荐商品", required = true)
    private boolean recommend;

    //@ApiModelProperty(value = "销售模式", required = true)
    private String salesModel;

    @Override
    public Date getUpdateTime() {
        if (super.getUpdateTime() == null) {
            return new Date(1593571928);
        } else {
            return super.getUpdateTime();
        }
    }
}
