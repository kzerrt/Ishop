package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.Menu;
import com.fc.ishop.dto.MenuSearchParams;
import com.fc.ishop.security.AuthUser;
import com.fc.ishop.vo.MenuVo;

import java.util.List;

public interface MenuService extends IService<Menu> {
    /**
     * 查找用户菜单权限
     */
    List<MenuVo> findUserTree();

    /**
     * @param currentUser 当前登录用户
     * @return
     */
    List<MenuVo> findUserTree(AuthUser currentUser);

    /**
     * 权限树形结构
     */
    List<MenuVo> tree();
    List<MenuVo> tree(List<Menu> userMenus);

    /**
     * 根据搜索条件查询菜单权限
     * @param searchParams
     * @return
     */
    List<Menu> searchList(MenuSearchParams searchParams);

    /**
     * 批量删除菜单
     * @param ids
     */
    void deleteIds(List<String> ids);
}
