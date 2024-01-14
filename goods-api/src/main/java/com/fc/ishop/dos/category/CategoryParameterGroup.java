package com.fc.ishop.dos.category;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fc.ishop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 分类绑定参数组
 * @author florence
 * @date 2023/12/12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("i_category_parameter_group")
public class CategoryParameterGroup extends BaseEntity {
    private static final long serialVersionUID = -3254446505349029420L;

    /**
     * 参数组名称
     */
    @NotEmpty(message = "参数组名称不能为空")
    @Length(max = 50, message = "参数组名称不能超过50字")
    private String groupName;
    /**
     * 关联分类id
     */
    @NotNull(message = "关联的分类不能为空")
    private String categoryId;
    /**
     *排序
     */
    private Integer sort;
}
