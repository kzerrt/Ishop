package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.UserRole;
import com.fc.ishop.mapper.UserRoleMapper;
import com.fc.ishop.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userRoleService")
public class UserRoleServiceImpl
        extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
    @Override
    public List<UserRole> listByUserId(String userId) {
        return null;
    }

    @Override
    public List<String> listId(String userId) {
        return null;
    }

    @Override
    public void updateUserRole(String userId, List<UserRole> userRoles) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        baseMapper.delete(queryWrapper);
        // 保存
        saveBatch(userRoles);
    }
}
