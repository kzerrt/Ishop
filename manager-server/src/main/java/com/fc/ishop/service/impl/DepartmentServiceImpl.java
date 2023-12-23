package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.AdminUser;
import com.fc.ishop.dos.Department;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.mapper.DepartmentMapper;
import com.fc.ishop.service.DepartmentRoleService;
import com.fc.ishop.service.DepartmentService;
import com.fc.ishop.service.UserService;
import com.fc.ishop.vo.DepartmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("departmentService")
@Transactional
public class DepartmentServiceImpl
        extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
    // 管理员业务层接口
    @Autowired
    private UserService userService;
    // 部门角色业务层接口
    @Autowired
    private DepartmentRoleService departmentRoleService;
    @Override
    public List<DepartmentVo> tree(QueryWrapper<Department> initWrapper) {
        List<Department> departments = this.list(initWrapper);
        List<DepartmentVo> tree = new LinkedList<>();
        // 默认情况下现有父部门，在有子部门
        // 记录所有部门id，存在多级目录
        Map<String, DepartmentVo> parents = new HashMap<>();
        departments.forEach(item -> {
            DepartmentVo departmentVo = new DepartmentVo(item);
            parents.put(item.getId(), departmentVo);
            if (item.getParentId().equals("0")) {
                tree.add(departmentVo);
            } else {
                DepartmentVo parentVo = parents.get(item.getParentId());
                if (parentVo != null) {
                    parentVo.getChildren().add(departmentVo);
                }
            }
        });
        // 对部门进行排序, 从小到大
        tree.sort(DepartmentVo::compareTo);
        return tree;
    }

    @Override
    public void deleteByIds(List<String> ids) {
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        // 查询部门中是否存在管理员
        if (userService.count(queryWrapper) > 0) {
            throw new ServiceException(ResultCode.PERMISSION_DEPARTMENT_DELETE_ERROR);
        }
        this.removeByIds(ids);
        // 部门删除，对应部门角色信息也要删除
        departmentRoleService.deleteByDepartmentIds(ids);
    }
}
