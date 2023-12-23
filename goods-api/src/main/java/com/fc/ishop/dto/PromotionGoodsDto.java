package com.fc.ishop.dto;

import com.fc.ishop.dos.goods.GoodsSku;
import com.fc.ishop.dos.trade.PromotionGoods;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 促销商品传输类
 * @author florence
 * @date 2023/12/22
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class PromotionGoodsDto extends PromotionGoods {
    private static final long serialVersionUID = 9206970681612883421L;

    //(value = "原价")
    private Double originPrice;

    //(value = "商品id")
    private String goodsId;

    //(value = "商品名称")
    private String goodsName;

    //(value = "商品图片")
    private String goodsImage;

    public PromotionGoodsDto(GoodsSku sku) {
        super(sku);
    }
}
