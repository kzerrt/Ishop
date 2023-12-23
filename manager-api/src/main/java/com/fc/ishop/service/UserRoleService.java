package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.UserRole;

import java.util.List;

public interface UserRoleService extends IService<UserRole> {

    /**
     * 根据用户查看拥有的角色
     *
     * @param userId
     * @return
     */
    List<UserRole> listByUserId(String userId);

    /**
     * 根据用户查看拥有的角色
     *
     * @param userId
     * @return
     */
    List<String> listId(String userId);

    /**
     * 更新用户拥有的角色
     *
     * @param userId
     * @return
     */
    void updateUserRole(String userId, List<UserRole> userRoles);
}
