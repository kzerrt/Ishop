package com.fc.ishop.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.category.CategoryBrand;
import com.fc.ishop.mapper.CategoryBrandMapper;
import com.fc.ishop.service.CategoryBrandService;
import com.fc.ishop.vo.category.CategoryBrandVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/11
 */
@Service("categoryBrandService")
@Transactional
public class CategoryBrandServiceImpl
        extends ServiceImpl<CategoryBrandMapper, CategoryBrand> implements CategoryBrandService {

    @Override
    public List<CategoryBrand> getCategoryBrandListByBrandId(String id) {
        return this.list(new LambdaQueryWrapper<CategoryBrand>().eq(CategoryBrand::getBrandId, id));
    }

    @Override
    public List<CategoryBrandVo> getCategoryBrandList(String categoryId) {

        return baseMapper.getCategoryBrandList(categoryId);
    }
}
