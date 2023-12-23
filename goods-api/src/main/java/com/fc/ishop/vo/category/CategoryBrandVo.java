package com.fc.ishop.vo.category;

import lombok.Data;

/**
 * @author florence
 * @date 2023/12/12
 */
@Data
public class CategoryBrandVo {

    /**
     * 品牌id
     */
    //@ApiModelProperty(value = "品牌id", required = true)
    private String id;

    /**
     * 品牌名称
     */
    //@ApiModelProperty(value = "品牌名称", required = true)
    private String name;
}
