package com.fc.ishop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fc.ishop.dos.category.CategorySpecification;
import com.fc.ishop.vo.category.CategorySpecificationVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/15
 */
public interface CategorySpecificationMapper extends BaseMapper<CategorySpecification> {
    /**
     * 根据分类id查分类绑定规格
     *
     * @param categoryId 分类id
     * @return 分类绑定规格列表
     */
    @Select("select s.id, s.spec_name as `name` from " +
            "i_specification s INNER join i_category_specification cs on s.id = cs.specification_id and cs.category_id = #{categoryId} " +
            "where s.delete_flag = 0")
    List<CategorySpecificationVo> getCategorySpecList(String categoryId);
}
