package com.fc.ishop.dto;

import com.fc.ishop.dos.MemberAddress;
import com.fc.ishop.enums.CartTypeEnum;
import com.fc.ishop.vo.order.CartSkuVo;
import com.fc.ishop.vo.order.CartVo;
import com.fc.ishop.vo.order.OrderVo;
import com.fc.ishop.vo.order.PriceDetailVo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车视图
 * @author florence
 * @date 2023/12/18
 */
@Data
public class TradeDto implements Serializable {

    private static final long serialVersionUID = -3137165707807057810L;

    //@ApiModelProperty(value = "sn")
    private String sn;

    //@ApiModelProperty(value = "是否为其他订单下的订单，如果是则为依赖订单的sn，否则为空")
    private String parentOrderSn;

    //@ApiModelProperty((value = "购物车列表")
    private List<CartVo> cartList;

    //@ApiModelProperty((value = "整笔交易中所有的规格商品")
    private List<CartSkuVo> skuList;

    //@ApiModelProperty((value = "购物车车计算后的总价")
    private PriceDetailVo priceDetailVo;

    //@ApiModelProperty((value = "购物车车计算后的总价")
    private PriceDetailDto priceDetailDto;


    //@ApiModelProperty((value = "是否需要发票")
    private Boolean needReceipt;


    //@ApiModelProperty((value = "不支持配送方式")
    private List<CartSkuVo> notSupportFreight;
    /**
     * 购物车类型
     */
    private CartTypeEnum cartTypeEnum;
    /**
     * 收货地址
     */
    private MemberAddress memberAddress;

    /**
     * 买家名称
     */
    private String memberName;

    /**
     * 买家id
     */
    private String memberId;

    /**
     * 订单Vo
     */
    private List<OrderVo> orderVo;

    public TradeDto(CartTypeEnum cartTypeEnum) {
        this.skuList = new ArrayList<>();
        this.cartList = new ArrayList<>();
        this.cartTypeEnum = cartTypeEnum;
        this.needReceipt = false;
    }

    public TradeDto() {
        this(CartTypeEnum.CART);
    }
}
