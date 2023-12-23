package com.fc.ishop.vo.order;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CartVo extends CartBase implements Serializable {

    private static final long serialVersionUID = -5651775413457562422L;

    //@ApiModelProperty(value = "购物车中的产品列表")
    private List<CartSkuVo> skuList;

    //@ApiModelProperty(value = "sn")
    private String sn;

    //@ApiModelProperty(value = "购物车页展示时，店铺内的商品是否全选状态.1为店铺商品全选状态,0位非全选")
    private Boolean checked;


    //@ApiModelProperty(value = "重量")
    private Double weight;

    //@ApiModelProperty(value = "购物车商品数量")
    private Integer goodsNum;

    //@ApiModelProperty(value = "购物车商品数量")
    private String remark;

    /**
     * DeliveryMethodEnum
     */
    //@ApiModelProperty(value = "配送方式")
    private String deliveryMethod;

    //@ApiModelProperty(value = "已参与的的促销活动提示，直接展示给客户")
    private String promotionNotice;

    public CartVo() {
    }

    public CartVo(CartSkuVo cartSkuVO) {
        this.setStoreId(cartSkuVO.getStoreId());
        this.setStoreName(cartSkuVO.getStoreName());
        this.setSkuList(new ArrayList<>());
        this.setChecked(false);
        this.weight = 0d;
        this.remark = "";
    }

    public void addGoodsNum(Integer goodsNum) {
        if (this.goodsNum == null) {
            this.goodsNum = goodsNum;
        } else {
            this.goodsNum += goodsNum;
        }
    }
}
