package com.fc.ishop.vo;

import com.fc.ishop.enums.SwitchEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author florence
 * @date 2023/12/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberSearchVo {
    //@ApiModelProperty(value = "用户名")
    private String username;

    //@ApiModelProperty(value = "昵称")
    private String nickName;

    //@ApiModelProperty(value = "用户手机号码")
    private String mobile;

    /**
     * @see SwitchEnum
     */
    //@ApiModelProperty(value = "会员状态")
    private String disabled;
}
