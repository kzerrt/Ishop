package com.fc.ishop.dos.category;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 分类品牌关联
 * @author florence
 * @date 2023/12/11
 */
@Data
@TableName("li_category_brand")
@NoArgsConstructor
public class CategoryBrand implements Serializable {
    private static final long serialVersionUID = 3315719881926878L;

    @TableId
    @TableField
    private String id;


    @CreatedBy
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 分类id
     */
    @TableField(value = "category_id")
    private String categoryId;
    /**
     * 品牌id
     */
    @TableField(value = "brand_id")
    private String brandId;

    public CategoryBrand(String categoryId,String brandId){
        this.categoryId=categoryId;
        this.brandId=brandId;
    }
}
