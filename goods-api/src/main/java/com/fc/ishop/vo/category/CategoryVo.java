package com.fc.ishop.vo.category;

import cn.hutool.core.bean.BeanUtil;
import com.fc.ishop.dos.Brand;
import com.fc.ishop.dos.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVo extends Category {
    private static final long serialVersionUID = 3775766246075838410L;

    //@ApiModelProperty(value = "父节点名称")
    private String parentTitle;

    //@ApiModelProperty("子分类列表")
    private List<CategoryVo> children;

    //@ApiModelProperty("分类关联的品牌列表")
    private List<Brand> brandList;

    public CategoryVo(Category category) {
        BeanUtil.copyProperties(category, this);
    }

    public List<CategoryVo> listChildren() {
        if (children == null) {
            children = new LinkedList<>();
        }
        return children;
    }

    public List<CategoryVo> getChildren() {

        if (children != null) {
            children.sort((CategoryVo o1, CategoryVo o2) -> {
                    return o1.getSortOrder().compareTo(o2.getSortOrder());
            });
            return children;
        }
        return null;
    }
}
