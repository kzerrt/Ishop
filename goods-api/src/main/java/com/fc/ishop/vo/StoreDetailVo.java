package com.fc.ishop.vo;

import com.fc.ishop.dto.StoreEditDto;
import lombok.Data;

/**
 * @author florence
 * @date 2023/12/18
 */
@Data
public class StoreDetailVo extends StoreEditDto {
    //@ApiModelProperty(value = "会员名称")
    private String memberName;
}
