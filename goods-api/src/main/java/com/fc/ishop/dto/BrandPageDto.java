package com.fc.ishop.dto;

import com.fc.ishop.vo.PageVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品查询
 * @author florence
 * @date 2023/12/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BrandPageDto extends PageVo {
    private static final long serialVersionUID = 8906820486037326039L;

    //@ApiModelProperty(value = "品牌名称")
    private String name;
}
