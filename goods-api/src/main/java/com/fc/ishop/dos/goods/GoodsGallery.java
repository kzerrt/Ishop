package com.fc.ishop.dos.goods;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;

import java.io.Serializable;

/**
 * 商品相册
 * @author florence
 * @date 2023/12/15
 */
@Data
@TableName("li_goods_gallery")
public class GoodsGallery implements Serializable {

    @TableId
    @TableField
    private String id;


    @CreatedBy
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 商品主键
     */
    private String goodsId;

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
     * 是否是默认图片1   0没有默认
     */
    private Integer isDefault;

    /**
     * 排序
     */
    private Integer sort;
}
