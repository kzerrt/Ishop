package com.fc.ishop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fc.ishop.dos.category.CategoryBrand;
import com.fc.ishop.vo.category.CategoryBrandVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/11
 */
public interface CategoryBrandMapper extends BaseMapper<CategoryBrand> {

    @Select("SELECT b.id,b.name FROM i_brand b INNER join i_category_brand cb on b.id = cb.brand_id and cb.category_id = #{categoryId} where b.delete_flag = 0")
    List<CategoryBrandVo> getCategoryBrandList(String categoryId);
}
