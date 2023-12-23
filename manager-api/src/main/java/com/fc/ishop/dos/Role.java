package com.fc.ishop.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fc.ishop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("i_role")
public class Role extends BaseEntity {
    private static final long serialVersionUID = 1L;

    //@ApiModelProperty(value = "角色名")
    private String name;

    //@ApiModelProperty(value = "是否为注册默认角色")
    private Boolean defaultRole;

    //@ApiModelProperty(value = "备注")
    private String description;
}
