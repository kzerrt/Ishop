package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.Menu;
import com.fc.ishop.dos.RoleMenu;
import com.fc.ishop.dto.MenuSearchParams;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.mapper.MenuMapper;
import com.fc.ishop.security.AuthUser;
import com.fc.ishop.service.MenuService;
import com.fc.ishop.service.RoleMenuService;
import com.fc.ishop.utils.PageUtil;
import com.fc.ishop.vo.MenuVo;
import com.fc.ishop.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service("menuService")
@Transactional
public class MenuServiceImpl
        extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Deprecated
    @Override // 由下面方法代替
    public List<MenuVo> findUserTree() {
        return null;
    }
    @Override
    public List<MenuVo> findUserTree(AuthUser currentUser) {
        if (currentUser.getIsSuper()) {
            return this.tree();
        }
        List<Menu> userMenus = baseMapper.findByUserId(currentUser.getId());
        return this.tree(userMenus);
    }

    @Override
    public List<MenuVo> tree() {
        try {
            List<Menu> menus = this.list();
            return tree(menus);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<MenuVo> tree(List<Menu> userMenus) {
        List<MenuVo> tree = new ArrayList<>();
        userMenus.forEach(menu -> {
            if (menu.getLevel() == 0) {
                MenuVo menuVo = new MenuVo(menu);
                initChild(menuVo, userMenus);
                tree.add(menuVo);
            }
        });
        // 对一级菜单排序
        tree.sort(MenuVo::compareTo);
        return tree;
    }

    @Override
    public List<Menu> searchList(MenuSearchParams searchParams) {
        String title = null;
        if (!StringUtils.isEmpty(searchParams.getTitle())) {
            title = searchParams.getTitle();
            searchParams.setTitle(null);
        }
        QueryWrapper<Menu> wrapper = PageUtil.initWrapper(searchParams, new SearchVo());
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        wrapper.orderByDesc("sort_order");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public void deleteIds(List<String> ids) {
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("menu_id", ids);
        // 删除菜单，如果有用户与菜单关联则不删除
        if (roleMenuService.count(queryWrapper) > 0) {
            throw new ServiceException(ResultCode.PERMISSION_MENU_ROLE_ERROR);
        }
        this.removeByIds(ids);
        // 删除关联关系
        //roleMenuService.deleteRoleMenu(ids);
    }

    private void initChild(MenuVo tree, List<Menu> menus) {
        if (menus == null) return;
        menus.stream()
                .filter(item -> tree.getId().equals(item.getParentId()))
                .forEach(child -> {
                    MenuVo childTree = new MenuVo(child);
                    initChild(childTree, menus);
                    tree.getChildren().add(childTree);
                });
    }
}
