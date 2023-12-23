package com.fc.ishop.vo.goods;

import cn.hutool.core.bean.BeanUtil;
import com.fc.ishop.dos.goods.GoodsSku;
import com.fc.ishop.vo.SpecValueVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 商品规格VO
 * @author florence
 * @date 2023/12/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsSkuVo extends GoodsSku{
    private static final long serialVersionUID = -7651149660489332344L;

    //@ApiModelProperty(value = "规格列表")
    private List<SpecValueVo> specList;

    //@ApiModelProperty(value = "商品图片")
    private List<String> goodsGalleryList;

    public GoodsSkuVo(GoodsSku goodsSku) {
        BeanUtil.copyProperties(goodsSku, this);
    }
}
