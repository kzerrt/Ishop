package com.fc.ishop.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fc.ishop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 规格项
 * @author florence
 * @date 2023/12/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("i_specification")
public class Specification extends BaseEntity {
    private static final long serialVersionUID = 147792597901239486L;

    /**
     * 规格名称
     */
    @NotEmpty(message = "规格名称不能为空")
    private String specName;

    /**
     * 所属卖家 0属于平台
     */
    private String storeId;

}
