package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.Role;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.mapper.RoleMapper;
import com.fc.ishop.service.DepartmentRoleService;
import com.fc.ishop.service.RoleMenuService;
import com.fc.ishop.service.RoleService;
import com.fc.ishop.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author florence
 * @date 2023/11/17
 */
@Service("roleService")
public class RoleServiceImpl
        extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private DepartmentRoleService departmentRoleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<Role> findByDefaultRole(boolean defaultRole) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("default_role", defaultRole);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public void deleteRoles(List<String> ids) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id", ids);
        // 查找部门关联角色
        if (departmentRoleService.count(queryWrapper) > 0) {
            throw new ServiceException(ResultCode.PERMISSION_DEPARTMENT_ROLE_ERROR);
        }
        // 查找用户关联角色
        if (userRoleService.count(queryWrapper) > 0) {
            throw new ServiceException(ResultCode.PERMISSION_USER_ROLE_ERROR);
        }
        // 删除角色
        this.removeByIds(ids);
        // 删除角色与菜单关联
        roleMenuService.deleteRoleMenu(ids);
    }
}
