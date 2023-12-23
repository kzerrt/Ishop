package com.fc.ishop.dos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fc.ishop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 规格值
 * @author florence
 * @date 2023/12/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("li_spec_values")
public class SpecValues extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 规格项id
     */
    @TableField(value = "spec_id")
    private String specId;

    /**
     * 规格值名字
     */
    @TableField(value = "spec_value")
    private String specValue;
}
