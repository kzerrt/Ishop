package com.fc.ishop.vo;

import com.fc.ishop.dos.AdminUser;
import com.fc.ishop.dos.Menu;
import com.fc.ishop.dos.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class AdminUserVo extends AdminUser {
    private static final long serialVersionUID = -2378384199695839312L;

    //@ApiModelProperty(value = "所属部门名称")
    private String departmentTitle;

    //@ApiModelProperty(value = "用户拥有角色")
    private List<Role> roles;

    //@ApiModelProperty(value = "用户拥有的权限")
    private List<Menu> menus;


    public AdminUserVo(AdminUser user) {
        BeanUtils.copyProperties(user, this);
    }
}
