package com.fc.ishop.dto;

import lombok.Data;

/**
 * 添加会员
 * @author florence
 * @date 2023/12/1
 */
@Data
public class MemberAddDto {
    //@ApiModelProperty(value = "会员用户名")
    private String username;

    //@ApiModelProperty(value = "会员密码")
    private String password;

    //@Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    private String mobile;
}
