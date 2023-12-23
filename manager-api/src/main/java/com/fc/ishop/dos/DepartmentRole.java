package com.fc.ishop.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fc.ishop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 角色部门绑定
 * @author florence
 * @date 2023/12/8
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("i_department_role")
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRole extends BaseEntity {
    private static final long serialVersionUID = 2342812932116647050L;

    //@ApiModelProperty(value = "角色id")
    private String roleId;

    //@ApiModelProperty(value = "部门id")
    private String departmentId;

}
