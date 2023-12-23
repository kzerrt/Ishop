package com.fc.ishop.vo.order;

import com.fc.ishop.dto.PriceDetailDto;
import lombok.Data;

import java.io.Serializable;

/**
 * @author florence
 * @date 2023/12/18
 */
@Data
public class PriceDetailVo implements Serializable {

    private static final long serialVersionUID = -960537582096338500L;

    //@ApiModelProperty(value = "商品原价")
    private Double originalPrice;

    //@ApiModelProperty(value = "配送费")
    private Double freight;

    //@ApiModelProperty(value = "最终成交金额")
    private Double finalePrice;


    /**
     * 构造器，初始化默认值
     */
    public PriceDetailVo(PriceDetailDto dto) {
        this.freight = dto.getFreightPrice();
        this.finalePrice = dto.getFlowPrice();
        this.originalPrice = dto.getGoodsPrice();
    }

    public PriceDetailVo(){

    }
}
