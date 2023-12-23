package com.fc.ishop.vo;

import com.fc.ishop.dos.category.Category;
import com.fc.ishop.utils.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author florence
 * @date 2023/12/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class StoreManagementCategoryVo extends Category {

    //@ApiModelProperty(value = "已选择")
    private boolean selected;

    public StoreManagementCategoryVo(Category category) {
        BeanUtil.copyProperties(this, category);
    }
}
