package com.fc.ishop.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fc.ishop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 商品品牌
 * @author florence
 * @date 2023/12/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("i_brand")
public class Brand extends BaseEntity {
    private static final long serialVersionUID = -8236865838438521426L;
    /**
     * 品牌名称
     */
    @NotEmpty(message = "品牌名称不能为空")
    private String name;
    /**
     * 品牌图标
     */
    @NotEmpty(message = "品牌图标不能为空")
    private String logo;
}
