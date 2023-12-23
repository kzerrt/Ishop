package com.fc.ishop.vo.order;

import com.fc.ishop.dto.PriceDetailDto;
import lombok.Data;

import java.io.Serializable;

/**
 * 购物车基础
 * @author florence
 * @date 2023/12/18
 */
@Data
public class CartBase implements Serializable {
    private static final long serialVersionUID = -5172752506920017597L;

    //@ApiModelProperty(value = "卖家id")
    private String storeId;

    //@ApiModelProperty(value = "卖家姓名")
    private String storeName;

   //@ApiModelProperty(value = "此商品价格流水计算")
    private PriceDetailDto priceDetailDto;

    //@ApiModelProperty(value = "此商品价格展示")
    private PriceDetailVo priceDetailVO;

    public CartBase() {
        priceDetailDto = new PriceDetailDto();
    }

    public PriceDetailVo getPriceDetailVO() {
        if (this.priceDetailDto != null) {
            return new PriceDetailVo(priceDetailDto);
        }
        return new PriceDetailVo();
    }
}
