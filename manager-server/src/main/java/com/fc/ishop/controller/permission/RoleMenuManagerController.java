package com.fc.ishop.controller.permission;

import com.fc.ishop.dos.RoleMenu;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.service.RoleMenuService;
import com.fc.ishop.vo.ResultMessage;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员 角色菜单接口
 * @author florence
 * @date 2023/12/8
 */
@RestController
@RequestMapping("/roleMenu")
public class RoleMenuManagerController {
    @Autowired
    private RoleMenuService roleMenuService;
    /**
     * 查看某角色拥有的菜单
     */
    @GetMapping("/{roleId}")
    public ResultMessage<List<RoleMenu>> get(@PathVariable String roleId) {
        return ResultUtil.data(roleMenuService.findByRoleId(roleId));
    }
    /**
     * 保存角色菜单
     */
    @PostMapping("/{roleId}")
    public ResultMessage<Object> save(@PathVariable String roleId, @RequestBody List<RoleMenu> roleMenus) {
        roleMenuService.updateRoleMenu(roleId, roleMenus);
        return ResultUtil.success();
    }
}
