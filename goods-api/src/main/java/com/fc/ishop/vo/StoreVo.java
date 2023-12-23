package com.fc.ishop.vo;

import lombok.Data;

/**
 * @author florence
 * @date 2023/12/18
 */
@Data
public class StoreVo {

    //@ApiModelProperty(value = "库存预警数量")
    private Integer stockWarning;

    //@ApiModelProperty(value = "登录用户的昵称")
    private String nickName;
}
