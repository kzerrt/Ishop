package com.fc.ishop.vo;

import com.fc.ishop.dos.goods.Goods;
import lombok.Data;

import java.io.Serializable;
import java.util.Random;

/**
 * @author florence
 * @date 2024/4/5
 */
@Data
public class SeckillGoodsVo implements Serializable {
    private static final long serialVersionUID = 5170316685407828228L;

    //(value = "活动id")
    private String seckillId;

    //(value = "时刻")
    private Integer timeLine;

    //(value = "商品id")
    private String goodsId;

    //(value = "skuID")
    private String skuId;

    //(value = "商品名称")
    private String goodsName;

    //(value = "商品图片")
    private String goodsImage;

    //(value = "商家id")
    private String storeId;

    //(value = "价格")
    private Double price;

    //(value = "促销数量")
    private Integer quantity;

    //(value = "已售数量")
    private Integer salesNum;

    //(value = "商品原始价格")
    private Double originalPrice;
    public SeckillGoodsVo() {

    }
    public SeckillGoodsVo(Goods goods) {
        goodsName = goods.getGoodsName();
        goodsId = goods.getId();
        skuId = goodsId;
        goodsId = goods.getThumbnail();
        price = goods.getPrice();
        quantity = goods.getQuantity();
        salesNum = goods.getBuyCount();
        originalPrice = price + new Random().nextDouble();
    }
}
