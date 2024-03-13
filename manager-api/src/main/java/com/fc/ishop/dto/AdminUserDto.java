package com.fc.ishop.dto;

import com.fc.ishop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 管理员入库
 * @author florence
 * @date 2023/11/17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminUserDto extends BaseEntity {
    private static final long serialVersionUID = 1L;

    //@ApiModelProperty(value = "用户名")
    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    //@ApiModelProperty(value = "密码")
    private String password;

    //@ApiModelProperty(value = "昵称")
    private String nickName;

    //@ApiModelProperty(value = "手机")
    private String mobile;

    private String avatar;

    //@ApiModelProperty(value = "邮件")
    private String email;

    //@ApiModelProperty(value = "描述/详情/备注")
    private String description;

    //@ApiModelProperty(value = "所属部门id")
    private String departmentId;

    private boolean isSuper;
}
