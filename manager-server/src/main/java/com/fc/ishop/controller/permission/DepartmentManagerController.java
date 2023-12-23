package com.fc.ishop.controller.permission;

import com.fc.ishop.dos.Department;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.service.DepartmentService;
import com.fc.ishop.utils.PageUtil;
import com.fc.ishop.vo.DepartmentVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员 部门管理接口
 * @author florence
 * @date 2023/12/8
 */
@RestController
@RequestMapping("/department")
public class DepartmentManagerController {
    @Autowired
    private DepartmentService departmentService;
    /**
     * 查看部门详情
     */
    @GetMapping("/{departId}")
    public ResultMessage<Department> get(@PathVariable String departId) {
        Department department = departmentService.getById(departId);
        return ResultUtil.data(department);
    }
    /**
     * 条件查询部门信息
     */
    @GetMapping
    public ResultMessage<List<DepartmentVo>> getByPage(Department department, SearchVo searchVo) {
        return ResultUtil.data(departmentService.tree(PageUtil.initWrapper(department, searchVo)));
    }
    /**
     * 新增部门
     */
    @PostMapping
    public ResultMessage<Department> save(Department department) {
        departmentService.save(department);
        return ResultUtil.data(department);
    }
    /**
     * 更新部门
     */
    @PutMapping("/{departId}")
    public ResultMessage<Department> update(@PathVariable String departId, Department department) {
        department.setId(departId);
        departmentService.updateById(department);
        return ResultUtil.data(department);
    }
    /**
     * 删除部门
     */
    @DeleteMapping("/{ids}")
    public ResultMessage<Object> delByIds(@PathVariable List<String> ids) {
        departmentService.deleteByIds(ids);
        return ResultUtil.success();
    }
}
