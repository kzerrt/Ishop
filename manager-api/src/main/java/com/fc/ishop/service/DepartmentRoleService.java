package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.DepartmentRole;

import java.util.List;

/**
 * 角色部门业务层
 * @author florence
 * @date 2023/12/8
 */
public interface DepartmentRoleService extends IService<DepartmentRole> {
    /**
     * 根据部门id批量删除角色信息
     * @param ids
     */
    void deleteByDepartmentIds(List<String> ids);

    /**
     * 查询部门角色
     * @param departId
     * @return
     */
    List<DepartmentRole> listByDepartmentId(String departId);

    /**
     * 更新部门角色
     * @param departId
     */
    void updateByDepartmentId(String departId, List<DepartmentRole> departmentRole);
}
