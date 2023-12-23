package com.fc.ishop.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fc.ishop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("i_department")
public class Department extends BaseEntity implements Comparable<Department> {
    private static final long serialVersionUID = 7050744476203499624L;
    //@ApiModelProperty(value = "部门名称")
    private String title;

    //@ApiModelProperty(value = "父id")
    private String parentId;

    //@ApiModelProperty(value = "排序值")
    //@Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;

    @Override
    public int compareTo(Department o) {
        if (o == null) return 0;
        return sortOrder.compareTo(o.getSortOrder());
    }
}
