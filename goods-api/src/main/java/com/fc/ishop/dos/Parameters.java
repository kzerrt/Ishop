package com.fc.ishop.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fc.ishop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 商品参数
 * @author florence
 * @date 2023/12/12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("li_parameters")
public class Parameters extends BaseEntity {
    private static final long serialVersionUID = -566510714456317006L;

    /**
     * 参数名称
     */
    @NotEmpty(message = "参数名称必填")
    @Length(max = 50, message = "参数名称不能超过50字")
    private String paramName;

    /**
     *选择值
     */
    private String options;
    /**
     *是否可索引，0 不显示 1 显示
     */
    @NotNull(message = "是否可索引必选")
    @Min(value = 0, message = "是否可索引传值不正确")
    @Max(value = 1, message = "是否可索引传值不正确")
    private Integer isIndex;
    /**
     *是否必填是  1    否   0
     */
    @NotNull(message = "是否必填必选")
    @Min(value = 0, message = "是否必填传值不正确")
    @Max(value = 1, message = "是否必填传值不正确")
    private Integer required;
    /**
     *参数分组id
     */
    @NotNull(message = "所属参数组不能为空")
    private String groupId;
    /**
     *分类id
     */
    private String categoryId;
    /**
     *排序
     */
    private Integer sort;
}
