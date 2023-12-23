package com.fc.ishop.dos;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fc.ishop.base.IdEntity;


@TableName("i_user_role")
public class UserRole extends IdEntity {
    //@ApiModelProperty(value = "用户唯一id")
    private String userId;

    //@ApiModelProperty(value = "角色唯一id")
    private String roleId;

    public UserRole(String userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public UserRole() {

    }
}
