package com.fc.ishop.dos.trade;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fc.ishop.base.BaseEntity;
import com.fc.ishop.dto.BasePromotion;
import com.fc.ishop.dto.PriceDetailDto;
import com.fc.ishop.dto.TradeDto;
import com.fc.ishop.enums.OrderComplaintStatusEnum;
import com.fc.ishop.enums.OrderItemAfterSaleStatusEnum;
import com.fc.ishop.utils.BeanUtil;
import com.fc.ishop.utils.SnowFlake;
import com.fc.ishop.vo.order.CartSkuVo;
import com.fc.ishop.vo.order.CartVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

/**
 * 子订单
 * @author florence
 * @date 2023/12/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("i_order_item")
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends BaseEntity {
    private static final long serialVersionUID = 2108971190191410182L;

    //@ApiModelProperty(value = "订单编号")
    private String orderSn;

    //@ApiModelProperty(value = "子订单编号")
    private String sn;

    //@ApiModelProperty(value = "单价")
    private Double unitPrice;

    //@ApiModelProperty(value = "小记")
    private Double subTotal;

    //@ApiModelProperty(value = "商品ID")
    private String goodsId;
    //@ApiModelProperty(value = "货品ID")
    private String skuId;
    //@ApiModelProperty(value = "销售量")
    private Integer num;
    //@ApiModelProperty(value = "交易编号")
    private String tradeSn;
    //@ApiModelProperty(value = "图片")
    private String image;
    //@ApiModelProperty(value = "商品名称")
    private String goodsName;
    //@ApiModelProperty(value = "分类ID")
    private String categoryId;
    //@ApiModelProperty(value = "快照id")
    private String snapshotId;
    //@ApiModelProperty(value = "规格json")
    private String specs;
    //@ApiModelProperty(value = "促销类型")
    private String promotionType;
    //@ApiModelProperty(value = "促销id")
    private String promotionId;
    //@ApiModelProperty(value = "销售金额")
    private Double goodsPrice;

    //@ApiModelProperty(value = "实际金额")
    private Double flowPrice;

    /**
     * @see OrderItemAfterSaleStatusEnum
     */
    //@ApiModelProperty(value = "售后状态")
    private String afterSaleStatus;

    //@ApiModelProperty(value = "价格详情")
    private String priceDetail;

    /**
     * @see OrderComplaintStatusEnum
     */
    //@ApiModelProperty(value = "投诉状态")
    private String complainStatus;

    //@ApiModelProperty(value = "交易投诉id")
    private String complainId;

    public OrderItem(CartSkuVo cartSkuVo, CartVo cartVo, TradeDto tradeDto) {
        String oldId = this.getId();
        BeanUtil.copyProperties(cartSkuVo.getGoodsSku(), this);
        BeanUtil.copyProperties(cartSkuVo.getPriceDetailDto(), this);
        BeanUtil.copyProperties(cartSkuVo, this);
        this.setId(oldId);
        if (cartSkuVo.getPriceDetailDto().getJoinPromotion() != null && !cartSkuVo.getPriceDetailDto().getJoinPromotion().isEmpty()) {
            this.setPromotionType(CollUtil.join(cartSkuVo.getPriceDetailDto().getJoinPromotion().stream().map(BasePromotion::getPromotionName).collect(Collectors.toList()), ","));
            this.setPromotionId(CollUtil.join(cartSkuVo.getPriceDetailDto().getJoinPromotion().stream().map(BasePromotion::getId).collect(Collectors.toList()), ","));
        }
        this.setAfterSaleStatus(OrderItemAfterSaleStatusEnum.NEW.name());
        this.setComplainStatus(OrderComplaintStatusEnum.NEW.name());
        this.setPriceDetailDto(cartSkuVo.getPriceDetailDto());
        this.setOrderSn(cartVo.getSn());
        this.setTradeSn(tradeDto.getSn());
        this.setImage(cartSkuVo.getGoodsSku().getThumbnail());
        this.setGoodsName(cartSkuVo.getGoodsSku().getGoodsName());
        this.setSkuId(cartSkuVo.getGoodsSku().getId());
        this.setCategoryId(cartSkuVo.getGoodsSku().getCategoryPath().substring(
                cartSkuVo.getGoodsSku().getCategoryPath().lastIndexOf(",") + 1
        ));
        this.setGoodsPrice(cartSkuVo.getGoodsSku().getPrice());
        this.setUnitPrice(cartSkuVo.getPurchasePrice());
        this.setSubTotal(cartSkuVo.getSubTotal());
        this.setSn(SnowFlake.createStr("OI"));


    }

    public PriceDetailDto getPriceDetailDto() {
        return JSONUtil.toBean(priceDetail, PriceDetailDto.class);
    }

    public void setPriceDetailDto(PriceDetailDto priceDetail) {
        this.priceDetail = JSONUtil.toJsonStr(priceDetail);
    }
}
