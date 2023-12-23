package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.category.CategoryBrand;
import com.fc.ishop.vo.category.CategoryBrandVo;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/11
 */
public interface CategoryBrandService extends IService<CategoryBrand> {
    /**
     * 根据品牌ID获取分类品牌关联信息
     *
     * @param id 品牌ID
     * @return 分类品牌关联信息
     */
    List<CategoryBrand> getCategoryBrandListByBrandId(String id);

    /**
     * 根据分类id查询品牌信息
     *
     * @param categoryId 分类id
     * @return 分类品牌关联信息列表
     */
    List<CategoryBrandVo> getCategoryBrandList(String categoryId);
}
