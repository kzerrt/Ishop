package com.fc.ishop.vo.goods;

import com.fc.ishop.dos.goods.Goods;
import com.fc.ishop.dos.goods.GoodsParams;
import lombok.Data;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/15
 */
@Data
public class GoodsVo extends Goods {
    private static final long serialVersionUID = 6377623919990713567L;

    //@ApiModelProperty(value = "分类名称")
    private List<String> categoryName;

    //@ApiModelProperty(value = "商品参数")
    private List<GoodsParams> goodsParamsList;

    //@ApiModelProperty(value = "商品图片")
    private List<String> goodsGalleryList;

    //@ApiModelProperty(value = "sku列表")
    private List<GoodsSkuVo> skuList;
}
