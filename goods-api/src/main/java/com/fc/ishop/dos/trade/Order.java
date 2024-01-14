package com.fc.ishop.dos.trade;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fc.ishop.base.BaseEntity;
import com.fc.ishop.dto.PriceDetailDto;
import com.fc.ishop.dto.TradeDto;
import com.fc.ishop.enums.*;
import com.fc.ishop.utils.BeanUtil;
import com.fc.ishop.vo.order.CartVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.Optional;

/**
 * 订单
 * @author florence
 * @date 2023/12/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("i_order")
public class Order extends BaseEntity {

    private static final long serialVersionUID = 2233811628066468683L;
    //@ApiModelProperty("订单编号")
    private String sn;

    //@ApiModelProperty("交易编号 关联Trade")
    private String tradeSn;

    //@ApiModelProperty(value = "店铺ID")
    private String storeId;

    //@ApiModelProperty(value = "店铺名称")
    private String storeName;

    //@ApiModelProperty(value = "会员ID")
    private String memberId;

    //@ApiModelProperty(value = "用户名")
    private String memberName;

    /**
     * @see OrderStatusEnum
     */
   // @ApiModelProperty(value = "订单状态")
    private String orderStatus;

    /**
     * @see PayStatusEnum
     */
    //@ApiModelProperty(value = "付款状态")
    private String payStatus;
    /**
     * @see DeliverStatusEnum
     */
    //@ApiModelProperty(value = "货运状态")
    private String deliverStatus;

    //@ApiModelProperty(value = "第三方付款流水号")
    private String receivableNo;

    //@ApiModelProperty(value = "支付方式")
    private String paymentMethod;

    //@ApiModelProperty(value = "支付时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date paymentTime;

    //@ApiModelProperty(value = "收件人姓名")
    private String consigneeName;

    //@ApiModelProperty(value = "收件人手机")
    private String consigneeMobile;

    /**
     * @see DeliveryMethodEnum
     */
    //@ApiModelProperty(value = "配送方式")
    private String deliveryMethod;

    //@ApiModelProperty(value = "地址名称， '，'分割")
    private String consigneeAddressPath;

    //@ApiModelProperty(value = "地址id，'，'分割 ")
    private String consigneeAddressIdPath;

    //@ApiModelProperty(value = "详细地址")
    private String consigneeDetail;

    //@ApiModelProperty(value = "总价格")
    private Double flowPrice;

    //@ApiModelProperty(value = "商品价格")
    private Double goodsPrice;

    //@ApiModelProperty(value = "运费")
    private Double freightPrice;

    //@ApiModelProperty(value = "优惠的金额")
    private Double discountPrice;

    //修改金额
    //@ApiModelProperty(value = "修改价格")
    private Double updatePrice;

    //@ApiModelProperty(value = "发货单号")
    private String logisticsNo;

    //@ApiModelProperty(value = "物流公司CODE")
    private String logisticsCode;

    //@ApiModelProperty(value = "物流公司名称")
    private String logisticsName;

    //@ApiModelProperty(value = "订单商品总重量")
    private Double weight;

    //@ApiModelProperty(value = "商品数量")
    private Integer goodsNum;

    //@ApiModelProperty(value = "买家订单备注")
    private String remark;

    //@ApiModelProperty(value = "订单取消原因")
    private String cancelReason;

    //@ApiModelProperty(value = "完成时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date completeTime;

    //@ApiModelProperty(value = "送货时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date logisticsTime;

    //@ApiModelProperty(value = "支付方式返回的交易号")
    private String payOrderNo;


    //@ApiModelProperty(value = "是否为其他订单下的订单，如果是则为依赖订单的sn，否则为空")
    private String parentOrderSn;

    //@ApiModelProperty(value = "是否为某订单类型的订单，如果是则为订单类型的id，否则为空")
    private String promotionId;

    /**
     * @see OrderTypeEnum
     */
    //@ApiModelProperty(value = "订单类型")
    private String orderType;

    //@ApiModelProperty(value = "价格详情")
    private String priceDetail;


    public Order() {

    }

    public Order(CartVo cartVO, TradeDto tradeDto) {
        String oldId = this.getId();
        if (tradeDto.getMemberAddress() != null) {
            BeanUtil.copyProperties(tradeDto.getMemberAddress(), this);
        }
        BeanUtil.copyProperties(tradeDto, this);
        BeanUtil.copyProperties(cartVO.getPriceDetailDto(), this);
        BeanUtil.copyProperties(cartVO, this);
        this.setId(oldId);
        this.setOrderType(OrderTypeEnum.NORMAL.name());
        //促销信息填充
        if (cartVO.getSkuList().get(0).getPromotions() != null) {
            Optional<String> pintuanId = cartVO.getSkuList().get(0).getPromotions().stream().filter(i -> i.getPromotionType().equals(PromotionTypeEnum.PINTUAN.name())).map(PromotionGoods::getPromotionId).findFirst();
            if (pintuanId.isPresent()) {
                promotionId = pintuanId.get();
                if (tradeDto.getParentOrderSn() == null) {
                    this.setParentOrderSn("");
                }
            }
        }
        this.setOrderStatus(OrderStatusEnum.UNPAID.name());
        this.setPayStatus(PayStatusEnum.UNPAID.name());
        this.setDeliverStatus(DeliverStatusEnum.UNDELIVERED.name());
        //如果有收货地址，才记录收货地址
        if (tradeDto.getMemberAddress() != null) {
            this.setConsigneeAddressIdPath(tradeDto.getMemberAddress().getConsigneeAddressIdPath());
            this.setConsigneeAddressPath(tradeDto.getMemberAddress().getConsigneeAddressPath());
            this.setConsigneeDetail(tradeDto.getMemberAddress().getDetail());
            this.setConsigneeMobile(tradeDto.getMemberAddress().getMobile());
            this.setConsigneeName(tradeDto.getMemberAddress().getName());
        }
        this.setTradeSn(tradeDto.getSn());
        this.setRemark(cartVO.getRemark());
        this.setFreightPrice(tradeDto.getPriceDetailDto().getFreightPrice());
    }

    public PriceDetailDto getPriceDetailDto() {

        try {
            return JSONUtil.toBean(priceDetail, PriceDetailDto.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void setPriceDetailDTO(PriceDetailDto priceDetail) {
        this.priceDetail = JSONUtil.toJsonStr(priceDetail);
    }

}
