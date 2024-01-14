package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.category.CategorySpecification;
import com.fc.ishop.mapper.CategorySpecificationMapper;
import com.fc.ishop.service.CategorySpecificationService;
import com.fc.ishop.vo.category.CategorySpecificationVo;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/15
 */
@Service("categorySpecificationService")
public class CategorySpecificationServiceImpl
        extends ServiceImpl<CategorySpecificationMapper, CategorySpecification> implements CategorySpecificationService {
    @Override
    public List<CategorySpecificationVo> getCategorySpecList(String categoryId) {
        return baseMapper.getCategorySpecList(categoryId);
    }

    @Override
    public List<CategorySpecification> getCategorySpecList(String[] categoryId) {
        return this.list(new LambdaQueryWrapper<CategorySpecification>().in(CategorySpecification::getCategoryId, Arrays.asList(categoryId)));
    }

    @Override
    public void saveSpecification(String categoryId, String[] categorySpecs) {
        // 将存在的规格删除
        baseMapper.delete(new QueryWrapper<CategorySpecification>().eq("category_id", categoryId));
        // 保存新的信息
        //绑定规格信息
        for (String specId : categorySpecs) {
            CategorySpecification categoryBrand = new CategorySpecification(categoryId, specId);
            baseMapper.insert(categoryBrand);
        }
    }
}
