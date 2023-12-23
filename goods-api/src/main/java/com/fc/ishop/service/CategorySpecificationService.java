package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.category.CategorySpecification;
import com.fc.ishop.vo.category.CategorySpecificationVo;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/15
 */
public interface CategorySpecificationService extends IService<CategorySpecification> {
    /**
     * 根据分类id查询规格信息
     *
     * @param categoryId 分类id
     * @return 分类规格关联信息
     */
    List<CategorySpecificationVo> getCategorySpecList(String categoryId);

    /**
     * 保存分类规格
     * @param categoryId
     * @param categorySpecs
     */
    void saveSpecification(String categoryId, String[] categorySpecs);
}
