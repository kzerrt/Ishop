package com.fc.ishop.dos.trade;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fc.ishop.base.BaseEntity;
import com.fc.ishop.dto.TradeDto;
import com.fc.ishop.utils.BeanUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 交易
 * @author florence
 * @date 2023/12/18
 */
@Data
@TableName("li_trade")
@NoArgsConstructor
public class Trade extends BaseEntity {
    private static final long serialVersionUID = 5177608752643561827L;

    //(value = "交易编号")
    private String sn;

    //(value = "买家id")
    private String memberId;

    //(value = "买家用户名")
    private String memberName;

    //(value = "支付方式")
    private String paymentMethod;

    /**
     *  cn.lili.modules.order.order.entity.enums.PayStatusEnum
     */
    //(value = "付款状态")
    private String payStatus;

    //(value = "总价格")
    private Double flowPrice;

    //(value = "原价")
    private Double goodsPrice;

    //(value = "运费")
    private Double freightPrice;

    //(value = "优惠的金额")
    private Double discountPrice;

    /**
     * e DeliveryMethodEnum
     */
    //(value = "配送方式")
    private String deliveryMethod;

    //(value = "收货人姓名")
    private String consigneeName;

    //(value = "收件人手机")
    private String consigneeMobile;

    //(value = "地址名称， '，'分割")
    private String consigneeAddressPath;

    //(value = "地址id，'，'分割 ")
    private String consigneeAddressIdPath;

    public Trade(TradeDto tradeDTO) {
        String originId = this.getId();
        if (tradeDTO.getMemberAddress() != null) {
            BeanUtil.copyProperties(tradeDTO.getMemberAddress(), this);
            this.setConsigneeMobile(tradeDTO.getMemberAddress().getMobile());
            this.setConsigneeName(tradeDTO.getMemberAddress().getName());
        }
        BeanUtil.copyProperties(tradeDTO, this);
        BeanUtil.copyProperties(tradeDTO.getPriceDetailDto(), this);
        this.setId(originId);
    }
}
