package com.fc.ishop.dos.category;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fc.ishop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 商品分类规格
 * @author florence
 * @date 2023/12/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("li_category_specification")
@NoArgsConstructor
@AllArgsConstructor
public class CategorySpecification extends BaseEntity {

    private static final long serialVersionUID = -4041367493342243147L;
    /**
     * 分类id
     */
    @TableField(value = "category_id")
    private String categoryId;
    /**
     * 规格id
     */
    @TableField(value = "specification_id")
    private String specificationId;

}
