package com.fc.ishop.controller.permission;

import com.fc.ishop.dos.DepartmentRole;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.service.DepartmentRoleService;
import com.fc.ishop.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员 部门角色接口
 * @author florence
 * @date 2023/12/8
 */
@RestController
@RequestMapping("/departmentRole")
public class DepartmentRoleManagerController {
    @Autowired
    private DepartmentRoleService departmentRoleService;

    /**
     * 查看部门拥有角色
     * @param departId
     * @return
     */
    @GetMapping("/{departId}")
    public ResultMessage<List<DepartmentRole>> get(@PathVariable String departId) {
        return ResultUtil.data(departmentRoleService.listByDepartmentId(departId));
    }
    /**
     * 更新部门角色
     */
    @PutMapping("/{departId}")
    public ResultMessage<Object> update(@PathVariable String departId, @RequestBody List<DepartmentRole> departmentRoles) {
        departmentRoleService.updateByDepartmentId(departId, departmentRoles);
        return ResultUtil.success();
    }
}
