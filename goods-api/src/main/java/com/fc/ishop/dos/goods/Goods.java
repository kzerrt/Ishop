package com.fc.ishop.dos.goods;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fc.ishop.base.BaseEntity;
import com.fc.ishop.dto.GoodsOperationDto;
import com.fc.ishop.enums.GoodsStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;

/**
 * @author florence
 * @date 2023/12/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("i_goods")
public class Goods extends BaseEntity {
    private static final long serialVersionUID = 370683495251252601L;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品编号
     */
    @Length(max = 30, message = "商品规格编号太长，不能超过30个字符")
    private String sn;

    //@ApiModelProperty(value = "品牌id")
    private String brandId;

    //@ApiModelProperty(value = "分类path")
    private String categoryPath;

    //@ApiModelProperty(value = "计量单位")
    private String goodsUnit;

    /**
     * 卖点
     */
    private String sellingPoint;

    /**
     * 重量
     */
    @Max(value = 99999999, message = "重量不能超过99999999")
    private Double weight;
    /**
     * 上架状态
     *
     * @see GoodsStatusEnum
     */
    private String marketEnable;
    /**
     * 商品价格
     */
    @Max(value = 99999999, message = "价格不能超过99999999")
    private Double price;
    /**
     * 成本价格
     */
    @Max(value = 99999999, message = "成本价格99999999")
    private Double cost;

    /**
     * 购买数量
     */
    private Integer buyCount;
    /**
     * 库存
     */
    @Max(value = 99999999, message = "库存不能超过99999999")
    private Integer quantity;
    /**
     * 商品好评率
     *
    private Double grade;
    /**
     * 缩略图路径
     */
    private String thumbnail;
    /**
     * 小图路径
     */
    private String small;
    /**
     * 原图路径
     */
    private String original;
    /**
     * 店铺分类id
     */
    private String storeCategoryPath;
    /**
     * 审核状态
     *
     * @see GoodsStatusEnum
     */
    private String isAuth;
    /**
     * 审核信息
     */
    private String authMessage;
    /**
     * 下架原因
     */
    private String underMessage;


    public Goods() {
    }

    public Goods(GoodsOperationDto goodsOperationDTO) {
        this.goodsName = goodsOperationDTO.getGoodsName();
        this.categoryPath = goodsOperationDTO.getCategoryPath();
        this.sn = goodsOperationDTO.getSn();
        this.price = goodsOperationDTO.getPrice();
        this.weight = goodsOperationDTO.getWeight();
        this.sellingPoint = goodsOperationDTO.getSellingPoint();
        this.goodsUnit = goodsOperationDTO.getGoodsUnit();
        this.cost = goodsOperationDTO.getCost();

    }
}
