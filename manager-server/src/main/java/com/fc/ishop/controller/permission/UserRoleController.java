package com.fc.ishop.controller.permission;

import com.fc.ishop.dos.UserRole;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.service.UserRoleService;
import com.fc.ishop.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员角色接口
 * @author florence
 * @date 2023/12/8
 */
@RestController
@RequestMapping("/userRole")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 查看管理员角色
     * 根据用户id获取角色id
     */
    @GetMapping("/{userId}")
    public ResultMessage<UserRole> get(@PathVariable String userId) {
        UserRole userRole = userRoleService.getById(userId);
        return ResultUtil.data(userRole);
    }

    /**
     * 更新角色菜单
     */
    @PutMapping("/{userId}")
    public ResultMessage<Object> update(@PathVariable String userId, List<UserRole> ids) {
        userRoleService.updateUserRole(userId, ids);
        return ResultUtil.success();
    }
}
