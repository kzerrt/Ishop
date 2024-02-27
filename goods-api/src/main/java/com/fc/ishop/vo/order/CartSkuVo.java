package com.fc.ishop.vo.order;

import com.fc.ishop.dos.goods.GoodsSku;
import com.fc.ishop.dos.trade.PromotionGoods;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CartSkuVo extends CartBase implements Serializable {


    private static final long serialVersionUID = -894598033321906974L;


    private String sn;
    /**
     * 对应的sku DO
     */
    private GoodsSku goodsSku;


    //@ApiModelProperty(value = "购买数量")
    private Integer num;

    //@ApiModelProperty(value = "购买时的成交价")
    private Double purchasePrice;

    //@ApiModelProperty(value = "小记")
    private Double subTotal;
    /**
     * 是否选中，要去结算 0:未选中 1:已选中，默认
     */
    //@ApiModelProperty(value = "是否选中，要去结算")
    private Boolean checked;


    //@ApiModelProperty(value = " 谁承担运费 BUYER：买家承担，STORE：卖家承担")
    private String freightPayer;

    //@ApiModelProperty(value = "是否免运费")
    private Boolean isFreeFreight;

    //@ApiModelProperty(value = "积分购买 积分数量")
    private Integer point;

    //@ApiModelProperty(value = "是否失效 ")
    private Boolean invalid;

    //@ApiModelProperty(value = "购物车商品错误消息")
    private String errorMessage;

    //@ApiModelProperty(value = "是否可配送")
    private Boolean isShip;

    /*@ApiModelProperty(value =
            "拼团id 如果是拼团购买 此值为拼团活动id，" +
                    "当pintuanId为空，则表示普通购买（或者拼团商品，单独购买）")*/
    private String pintuanId;

    //@ApiModelProperty(value = "可参与的单品活动")
    private List<PromotionGoods> promotions;

    //@ApiModelProperty(value = "参与促销活动更新时间(一天更新一次) 例如时间为：2020-01-01  00：00：01")
    private Date updatePromotionTime;


    /**
     * 在构造器里初始化促销列表，规格列表
     */
    public CartSkuVo(GoodsSku goodsSku) {
        this.goodsSku = goodsSku;
        this.checked = true;
        this.invalid = false;
        //默认时间为0，让系统为此商品更新缓存
        this.updatePromotionTime = new Date(0);
        this.errorMessage = "";
        this.isShip = true;
        this.purchasePrice = goodsSku.getIsPromotion() != null && goodsSku.getIsPromotion() ? goodsSku.getPromotionPrice() : goodsSku.getPrice();
        this.isFreeFreight = false;
        this.freightPayer = goodsSku.getFreightPayer();
        this.setStoreId(goodsSku.getStoreId());
        this.setStoreName(goodsSku.getStoreName());
    }
}
