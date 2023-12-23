package com.fc.ishop.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fc.ishop.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;


/**
 * 管理员类
 *
 */
@Getter
@Setter
@TableName("i_admin_user")
public class AdminUser extends BaseEntity {

    private static final long serialVersionUID = 2918352800205024873L;

    //@ApiModelProperty(value = "用户名")
    private String username;

    //@ApiModelProperty(value = "密码")
    private String password;

    //@ApiModelProperty(value = "昵称")
    private String nickName;

    //@ApiModelProperty(value = "手机")
    private String mobile;

    //@ApiModelProperty(value = "邮件")
    private String email;

    //@ApiModelProperty(value = "用户头像")
    private String avatar ;

    //@ApiModelProperty(value = "是否是超级管理员 超级管理员/普通管理员")
    private Boolean isSuper = false;

    //@ApiModelProperty(value = "状态 默认true正常 false禁用")
    private Boolean status = true;

    //@ApiModelProperty(value = "描述/详情/备注")
    private String description;

    //@ApiModelProperty(value = "所属部门id")
    private String departmentId;
    /**
     * 冗余字段
     */
    //@ApiModelProperty(value = "角色id集合")
    private String roleIds;

}
