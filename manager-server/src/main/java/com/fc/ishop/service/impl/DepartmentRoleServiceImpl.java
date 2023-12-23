package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.DepartmentRole;
import com.fc.ishop.mapper.DepartmentRoleMapper;
import com.fc.ishop.service.DepartmentRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/8
 */
@Service("departmentRoleService")
@Transactional
public class DepartmentRoleServiceImpl
        extends ServiceImpl<DepartmentRoleMapper, DepartmentRole> implements DepartmentRoleService {
    @Override
    public void deleteByDepartmentIds(List<String> ids) {
        QueryWrapper<DepartmentRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("department_id", ids);
        baseMapper.delete(queryWrapper);
    }

    @Override
    public List<DepartmentRole> listByDepartmentId(String departId) {
        QueryWrapper<DepartmentRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("department_id", departId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public void updateByDepartmentId(String departId, List<DepartmentRole> departmentRole) {
        QueryWrapper<DepartmentRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("department_id", departId);
        baseMapper.delete(queryWrapper);

        this.saveBatch(departmentRole);
    }
}
