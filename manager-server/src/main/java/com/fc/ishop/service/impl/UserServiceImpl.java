package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.AdminUser;
import com.fc.ishop.dos.Department;
import com.fc.ishop.dos.Role;
import com.fc.ishop.dos.UserRole;
import com.fc.ishop.dto.AdminUserDto;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.mapper.UserMapper;
import com.fc.ishop.security.AuthUser;
import com.fc.ishop.service.DepartmentService;
import com.fc.ishop.service.RoleService;
import com.fc.ishop.service.UserRoleService;
import com.fc.ishop.service.UserService;
import com.fc.ishop.token.ManagerTokenGenerate;
import com.fc.ishop.token.Token;
import com.fc.ishop.utils.BeanUtil;
import com.fc.ishop.utils.CommonUtil;
import com.fc.ishop.utils.StringUtils;
import com.fc.ishop.vo.AdminUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.ServerError;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("userService")
@Transactional
public class UserServiceImpl
        extends ServiceImpl<UserMapper, AdminUser> implements UserService {
    @Autowired
    private ManagerTokenGenerate managerTokenGenerate;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DepartmentService departmentService;

    @Override
    public IPage<AdminUserVo> adminUserPage(Page initPage, QueryWrapper<AdminUser> initWrapper) {
        //根据查询条件和搜索条件查询记录
        Page<AdminUser> adminUserPage = page(initPage, initWrapper);
        // 获取角色和部门信息
        List<Role> roles = roleService.list();
        List<Department> departments = departmentService.list();

        List<AdminUserVo> result = new ArrayList<>();
        // 将条件获取的账号查找部门信息
        adminUserPage.getRecords().forEach(adminUser -> {
            AdminUserVo adminUserVo = new AdminUserVo(adminUser);
            String departmentId = adminUser.getDepartmentId();
            if (!StringUtils.isEmpty(departmentId)) {
                try {
                    adminUserVo.setDepartmentTitle(
                            departments.stream()
                                    .filter(department -> department.getId().equals(departmentId))
                                    .collect(Collectors.toList())
                                    .get(0)
                                    .getTitle()
                    );
                } catch (Exception e) {
                    log.error("填充部门异常", e);
                }
            }
            if (!StringUtils.isEmpty(adminUser.getRoleIds())) {
                List<String> memberRoles = Arrays.asList(adminUser.getRoleIds().split(","));
                try {
                    adminUserVo.setRoles(
                            roles.stream()
                                    .filter(role -> memberRoles.contains(role.getId()))
                                    .collect(Collectors.toList())
                    );
                } catch (Exception e) {
                    log.error("填充角色失败", e);
                }
            }
            result.add(adminUserVo);
        });
        // 整合
        Page<AdminUserVo> pageResult = new Page<>(adminUserPage.getCurrent(), adminUserPage.getSize(), adminUserPage.getTotal());
        pageResult.setRecords(result);
        return pageResult;
    }

    @Override
    public AdminUser findByUsername(String username) {
        LambdaQueryWrapper<AdminUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdminUser::getUsername, username);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean updateAdminUser(AdminUser adminUser, List<String> roles) {
        if (roles != null) {
            if (roles.size() > 10) {
                throw new ServiceException(ResultCode.PERMISSION_BEYOND_TEN);
            }
            if (roles.size() > 0) {
                adminUser.setRoleIds(StringUtils.join(",", roles));
            }
        }

        // 更新用户信息
        //CommonUtil.updateOpt(adminUser, adminUser.getUsername());
        updateById(adminUser);
        updateRole(adminUser.getId(), roles);
        return true;
    }


    @Override
    public void editPassword(AuthUser tokenUser, String password, String newPassword) {
        AdminUser user = getById(tokenUser.getId());
        // 校验旧密码
        if (!user.getPassword().equals(password)) {
            throw new ServiceException(ResultCode.USER_OLD_PASSWORD_ERROR);
        }
        // 检验新密码
        if (password.equals(newPassword)) {
            throw new ServiceException(ResultCode.USER_SIMILAR_PASSWORD);
        }
        user.setPassword(newPassword);
        this.updateById(user);
    }

    @Override
    public void resetPassword(List<String> ids) {
        LambdaQueryWrapper<AdminUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(AdminUser::getId, ids);
        List<AdminUser> adminUserList = this.list(queryWrapper);
        String password = StringUtils.md5("123456");
        if (adminUserList != null && !adminUserList.isEmpty()) {
            adminUserList.forEach(item -> item.setPassword(password));
            this.updateBatchById(adminUserList);
        }
    }

    @Override
    public void saveAdminUser(AdminUserDto adminUser, List<String> roles) {
        AdminUser dbUser = new AdminUser();
        BeanUtil.copyProperties(adminUser, dbUser);
        if (roles != null) {
            if (roles.size() > 10) {
                throw new ServiceException(ResultCode.PERMISSION_BEYOND_TEN);
            }
            if (roles.size() > 0) {
                dbUser.setRoleIds(StringUtils.join(",", roles));
            }
        }
        this.save(dbUser);
        updateRole(dbUser.getId(), roles);
    }

    @Override
    public void deleteCompletely(List<String> ids) {
        // 彻底删除超级管理员
        this.removeByIds(ids);
        // 删除管理员角色
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("user_id", ids);
        userRoleService.remove(queryWrapper);
    }

    @Override
    public Token login(String username, String password) {
        AdminUser user = this.findByUsername(username);
        if (user == null || !user.getStatus()) {
            throw new ServiceException(ResultCode.USER_PASSWORD_ERROR);
        }
        if (!user.getPassword().equals(password)) {
            throw new ServiceException(ResultCode.USER_PASSWORD_ERROR);
        }
        try {
            return managerTokenGenerate.createToken(user, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object refreshToken(String refreshToken) {
        return managerTokenGenerate.refreshToken(refreshToken);
    }

    /**
     * 更新用户角色
     */
    private void updateRole(String userId, List<String> roles) {
        if (roles == null || roles.size() == 0) {
            return;
        }
        List<UserRole> userRoles = new ArrayList<>(roles.size());
        roles.forEach(id -> userRoles.add(new UserRole(userId, id)));
        userRoleService.updateUserRole(userId, userRoles);
    }
}
