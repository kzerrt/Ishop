package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.Role;

import java.util.List;

/**
 * @author florence
 * @date 2023/11/17
 */
public interface RoleService extends IService<Role> {
    /**
     * 查找默认角色
     * @param defaultRole
     * @return
     */
    List<Role> findByDefaultRole(boolean defaultRole);
    /**
     * 批量删除角色
     * @param ids
     */
    void deleteRoles(List<String> ids);
}
