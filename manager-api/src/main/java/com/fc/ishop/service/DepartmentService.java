package com.fc.ishop.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.Department;
import com.fc.ishop.vo.DepartmentVo;

import java.util.List;

public interface DepartmentService extends IService<Department> {
    /**
     * 将部门以树状展示
     * @param initWrapper
     * @return
     */
    List<DepartmentVo> tree(QueryWrapper<Department> initWrapper);

    /**
     * 批量删除部门
     * @param ids
     */
    void deleteByIds(List<String> ids);
}
