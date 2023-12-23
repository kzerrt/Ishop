package com.fc.ishop.vo;

import com.fc.ishop.dos.Department;
import com.fc.ishop.utils.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/8
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DepartmentVo extends Department {
    private List<DepartmentVo> children = new LinkedList<>();
    public DepartmentVo() {

    }
    public DepartmentVo(Department department) {
        BeanUtil.copyProperties(department, this);
    }


}
