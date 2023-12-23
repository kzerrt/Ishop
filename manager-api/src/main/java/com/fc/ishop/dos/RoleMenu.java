package com.fc.ishop.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fc.ishop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("i_role_menu")
public class RoleMenu extends BaseEntity {
    private static final long serialVersionUID = -4680260092546996026L;

    //@ApiModelProperty(value = "角色id")
    private String roleId;

    //@ApiModelProperty(value = "菜单")
    private String menuId;

    //@ApiModelProperty(value = "是否拥有操作数据权限，为否则只有查看权限")
    private Boolean isSuper;
}
