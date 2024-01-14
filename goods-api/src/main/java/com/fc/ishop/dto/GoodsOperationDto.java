package com.fc.ishop.dto;

import com.fc.ishop.dos.goods.GoodsParams;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author florence
 * @date 2023/12/11
 */
@Data
@ToString
public class GoodsOperationDto implements Serializable {
    private static final long serialVersionUID = -509667581371776913L;

    //@ApiModelProperty(hidden = true)
    private String goodsId;

    //@ApiModelProperty(value = "分类path")
    private String categoryPath;

    //@ApiModelProperty(value = "店铺分类id", required = true)
    @NotNull(message = "店铺分类不能为空")
    @Min(value = 0, message = "店铺分类值不正确")
    private String storeCategoryPath;

    //@ApiModelProperty(value = "品牌id")
    @Min(value = 0, message = "品牌值不正确")
    private String brandId;

    //@ApiModelProperty(value = "商品名称", required = true)
    @NotEmpty(message = "商品名称不能为空")
    private String goodsName;

    //@ApiModelProperty(value = "商品编号", required = true)
    @Length(max = 30, message = "商品编号太长，不能超过30个字符")
    private String sn;

    //@ApiModelProperty(value = "商品价格", required = true)
    @NotNull(message = "商品价格不能为空")
    @Min(value = 0, message = "商品价格不能为负数")
    @Max(value = 99999999, message = "商品价格不能超过99999999")
    private Double price;

    //@ApiModelProperty(value = "市场价格", required = true)
    @NotNull(message = "市场价格不能为空")
    private Double cost;

    //@ApiModelProperty(value = "重量", required = true)
    @NotNull(message = "商品重量不能为空")
    @Min(value = 0, message = "重量不能为负数")
    @Max(value = 99999999, message = "重量不能超过99999999")
    private Double weight;

    //@ApiModelProperty(value = "详情")
    private String intro;


    //@ApiModelProperty(value = "商品参数")
    @Valid
    private List<GoodsParams> goodsParamsList;

    //@ApiModelProperty(value = "商品图片")
    private List<String> goodsGalleryList;

    //@ApiModelProperty(value = "sku列表")
    @Valid
    private List<Map<String, Object>> skuList;

    //@ApiModelProperty(value = "卖点")
    private String sellingPoint;

    //@ApiModelProperty(value = "是否有规格", hidden = true)
    private String haveSpec;

    //@ApiModelProperty(value = "销售模式", required = true)
    private String goodsUnit;

    //@ApiModelProperty(value = "是否重新生成sku数据")
    private Boolean regeneratorSkuFlag = true;
}
