package com.fc.ishop.controller.permission;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dos.Role;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.service.RoleService;
import com.fc.ishop.utils.PageUtil;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 管理员 角色管理接口
 * @author florence
 * @date 2023/12/8
 */
@RestController
@RequestMapping("/role")
public class RoleManagerController {
    @Autowired
    private RoleService roleService;
    /**
     * 添加角色
     */
    @PostMapping
    public ResultMessage<Role> add(Role role) {
        roleService.save(role);
        return ResultUtil.data(role);
    }
    /**
     * 查询角色
     */
    @GetMapping
    public ResultMessage<Page<Role>> query(PageVo pageVo, Role role) {
        Page<Role> page = roleService.page(PageUtil.initPage(pageVo), PageUtil.initWrapper(role));
        return ResultUtil.data(page);
    }
    /**
     * 编辑角色
     */
    @PutMapping("/{roleId}")
    public ResultMessage<Role> edit(@PathVariable String roleId, Role role) {
        role.setId(roleId);
        roleService.updateById(role);
        return ResultUtil.data(role);
    }
    /**
     * 批量删除
     */
    @DeleteMapping("/{ids}")
    public ResultMessage<Object> delById(@PathVariable List<String> ids) {
        roleService.deleteRoles(ids);
        return ResultUtil.success();
    }
}
