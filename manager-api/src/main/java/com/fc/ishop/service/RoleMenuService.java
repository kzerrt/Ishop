package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.RoleMenu;
import com.fc.ishop.vo.UserMenuVo;

import java.util.List;

/**
 * 用户菜单接口
 */
public interface RoleMenuService extends IService<RoleMenu> {
    /**
     * 通过角色获取菜单权限列表
     *
     * @param roleId
     * @return
     */
    List<RoleMenu> findByRoleId(String roleId);


    /**
     * 根据角色集合获取拥有的菜单具体权限
     *
     * @param userId
     * @return
     */
    List<UserMenuVo> findAllMenu(String userId);


    /**
     * 更新某角色拥有到菜单
     *
     * @param roleId
     * @param roleMenus
     */
    void updateRoleMenu(String roleId, List<RoleMenu> roleMenus);

    /**
     * 根据角色id 删除数据
     *
     * @param roleId
     */
    void deleteRoleMenu(String roleId);

    /**
     * 根据角色id 删除数据
     *
     * @param roleId
     */
    void deleteRoleMenu(List<String> roleId);
}
