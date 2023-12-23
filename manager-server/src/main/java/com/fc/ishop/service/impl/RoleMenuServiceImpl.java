package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.RoleMenu;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.mapper.MenuMapper;
import com.fc.ishop.mapper.RoleMenuMapper;
import com.fc.ishop.service.RoleMenuService;
import com.fc.ishop.vo.UserMenuVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service("roleMenuService")
@Transactional
public class RoleMenuServiceImpl
        extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public List<RoleMenu> findByRoleId(String roleId) {
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<UserMenuVo> findAllMenu(String userId) {
        return menuMapper.getUserRoleMenu(userId);
    }

    @Override
    public void updateRoleMenu(String roleId, List<RoleMenu> roleMenus) {
        try {
            // 删除角色已经绑定的菜单信息
            this.deleteRoleMenu(roleId);
            // 将新的菜单信息保存
            this.saveBatch(roleMenus);
        } catch (Exception e) {
            log.warn(" 用户 {}更新用户菜单失败 {}", roleId, e.getMessage());
            throw new ServiceException(ResultCode.PERMISSION_ROLE_MENU_ERROR);
        }
    }

    @Override
    public void deleteRoleMenu(String roleId) {

    }

    @Override
    public void deleteRoleMenu(List<String> roleId) {

    }
}
