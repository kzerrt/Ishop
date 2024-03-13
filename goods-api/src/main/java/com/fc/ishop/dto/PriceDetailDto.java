package com.fc.ishop.dto;

import com.fc.ishop.util.CurrencyUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/18
 */
@Deprecated
@Data
public class PriceDetailDto implements Serializable {

    //用于订单价格修改金额计算使用
    //@ApiModelProperty(value = "订单原始总价格" )
    private Double originalPrice;

    private static final long serialVersionUID = 8808470688518188146L;
    //@ApiModelProperty(value = "商品总金额（商品原价）")
    private Double goodsPrice;

    //@ApiModelProperty(value = "配送费")
    private Double freightPrice;



    //========= update price ==========
    //@ApiModelProperty(value = "订单修改金额")
    private Double updatePrice;

    //=========end update price==========
    //@ApiModelProperty(value = "流水金额(入账 出帐金额) = goodsPrice + freight - discountPrice - couponPrice + updatePrice")
    private Double flowPrice;

    //@ApiModelProperty(value = "最终结算金额 = flowPrice - platFormCommission - distributionCommission")
    private Double billPrice;

    /**
     * 参与的促销活动
     */
    //@ApiModelProperty(value = "参与的促销活动")
    private List<BasePromotion> joinPromotion;


    public PriceDetailDto() {
        goodsPrice = 0d;
        freightPrice = 0d;


        updatePrice = 0d;

        flowPrice = 0d;
        billPrice = 0d;

        joinPromotion = new ArrayList<>();
    }

    /**
     * 累加
     *
     * @param priceDetailDTOS
     * @return
     */
    public static PriceDetailDto accumulationPriceDTO(List<PriceDetailDto> priceDetailDTOS, PriceDetailDto originPriceDetail) {


        double goodsPrice = 0d;
        double freightPrice = 0d;

        int payPoint = 0;
        double discountPrice = 0d;

        double distributionCommission = 0d;
        double platFormCommission = 0d;

        double siteCouponPrice = 0d;
        double siteCouponPoint = 0d;
        double siteCouponCommission = 0d;

        double updatePrice = 0d;

        double flowPrice = 0d;
        double billPrice = 0d;

        for (PriceDetailDto price : priceDetailDTOS) {
            goodsPrice = CurrencyUtil.add(goodsPrice, price.getGoodsPrice());
            freightPrice = CurrencyUtil.add(freightPrice, price.getFreightPrice());

            updatePrice = CurrencyUtil.add(updatePrice, price.getUpdatePrice());



            flowPrice = CurrencyUtil.add(flowPrice, price.getFlowPrice());
            billPrice = CurrencyUtil.add(billPrice, price.getBillPrice());

        }
        originPriceDetail.setGoodsPrice(goodsPrice);
        originPriceDetail.setFreightPrice(freightPrice);
        originPriceDetail.setUpdatePrice(updatePrice);


        originPriceDetail.setFlowPrice(flowPrice);
        originPriceDetail.setBillPrice(billPrice);

        return originPriceDetail;
    }

    /**
     * 累加
     *
     * @param priceDetailDTOS
     * @return
     */
    public static Double sumGoodsPrice(List<PriceDetailDto> priceDetailDTOS) {


        double goodsPrice = 0d;

        for (PriceDetailDto price : priceDetailDTOS) {

            goodsPrice = CurrencyUtil.add(goodsPrice, price.getGoodsPrice());

        }

        return goodsPrice;
    }

    /**
     * 自身计价
     */
    public void count() {
        this.flowPrice = CurrencyUtil.add(goodsPrice, freightPrice);
        //this.billPrice = CurrencyUtil.sub(CurrencyUtil.sub(CurrencyUtil.sub(flowPrice, platFormCommission)), distributionCommission);
    }
}
