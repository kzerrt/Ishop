package com.fc.ishop.token;

import com.fc.ishop.cache.Cache;
import com.fc.ishop.cache.CachePrefix;
import com.fc.ishop.dos.AdminUser;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.enums.UserEnums;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.security.AuthUser;
import com.fc.ishop.service.RoleMenuService;
import com.fc.ishop.service.UserService;
import com.fc.ishop.token.generate.AbstractTokenGenerate;
import com.fc.ishop.vo.UserMenuVo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 *  管理端生成token类
 */
@Slf4j
@Component
public class ManagerTokenGenerate extends AbstractTokenGenerate {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private Cache<String> cache;
    @Autowired
    private TokenUtil tokenUtil;
    @Override
    public Token createToken(String username, Boolean longTerm) {
        AdminUser admin = userService.findByUsername(username);
        return createToken(admin, longTerm);
    }

    /**
     * 1. 获取管理对象
     * 2. 查询对象权限，将权限放入缓存
     * 3. 使用工具创建token
     */
    @Override
    public Token createToken(Object adminUser, Boolean longTerm) {
        AuthUser user = null;
        if (adminUser instanceof AdminUser) {
            // 对象强制转换
            AdminUser admin = (AdminUser) adminUser;
            user = new AuthUser(admin.getUsername(), admin.getId(), UserEnums.MANAGER, admin.getNickName(), admin.getIsSuper());
            // 根据角色集合获取获取拥有菜单的具体权限
            List<UserMenuVo> userMenuVoList = roleMenuService.findAllMenu(user.getId());
            //log.debug("管理员获取角色权限");
            // redis缓存权限列表
            Gson gson = new Gson();
            cache.put(CachePrefix.PERMISSION_LIST.getPrefix(UserEnums.MANAGER) + user.getId(),
                    gson.toJson(permissionList(userMenuVoList)),
                    60L, TimeUnit.MINUTES);
        } else if (adminUser instanceof AuthUser) {
            user = (AuthUser) adminUser;
        } else {
            throw new ServiceException(ResultCode.USER_TOKEN_ERROR);
        }
        //return tokenUtil.createToken(admin.getUsername(), UserEnums.MANAGER);
        return tokenUtil.createToken(user);
    }

    @Override
    public Token refreshToken(String refreshToken) {
        return tokenUtil.refreshToken(refreshToken, UserEnums.MANAGER);
    }

    private Map<String, List<String>> permissionList(List<UserMenuVo> userMenuVoList) {
        Map<String, List<String>> permission = new HashMap<>();
        if (userMenuVoList == null || userMenuVoList.size() == 0) {
            return permission;
        }
        List<String> superPermissions = new ArrayList<>();
        List<String> queryPermissions = new ArrayList<>();
        initPermission(superPermissions, queryPermissions);

        userMenuVoList.forEach( menu -> {

            if (menu.getPath() != null) {
                String[] paths = menu.getPath().split("\\|");
                for (String path : paths) {
                    // 如果是超级权限，则计入超级权限
                    if (menu.getIsSupper()) {
                        if (!superPermissions.contains(path)) {
                            superPermissions.add(path);
                        }
                    } else { // 否则计入浏览权限
                        // 两者都不存在，将路径添加
                        if (!superPermissions.contains(path) && !queryPermissions.contains(path)) {
                            queryPermissions.add(path);
                        }
                    }
                }
            }
            // 去除无效权限，将超级权限中的路径在普通权限中删除
            superPermissions.forEach(queryPermissions::remove);
        });
        permission.put("SUPER", superPermissions);
        permission.put("QUERY", queryPermissions);
        return permission;
    }

    /**
     * 初始赋予的权限，查看权限包含首页流量统计权限，
     * 超级权限包含个人信息维护，密码修改权限
     *
     * @param superPermissions 超级权限
     * @param queryPermissions 查询权限
     */
    void initPermission(List<String> superPermissions, List<String> queryPermissions) {
        //用户信息维护
        superPermissions.add("/manager/user/info");
        superPermissions.add("/manager/user/edit");
        superPermissions.add("/manager/user/editPassword*");
        //统计查看
        queryPermissions.add("/manager/statistics*");
    }
}
