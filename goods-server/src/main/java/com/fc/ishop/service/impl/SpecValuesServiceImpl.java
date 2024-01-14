package com.fc.ishop.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.SpecValues;
import com.fc.ishop.dos.Specification;
import com.fc.ishop.mapper.SpecValuesMapper;
import com.fc.ishop.service.SpecValuesService;
import com.fc.ishop.service.SpecificationService;
import com.fc.ishop.utils.PageUtil;
import com.fc.ishop.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/15
 */
@Service("specValuesService")
public class SpecValuesServiceImpl
        extends ServiceImpl<SpecValuesMapper, SpecValues> implements SpecValuesService {

    @Autowired
    private SpecificationService specificationService;

    @Override
    public List<SpecValues> saveSpecValue(String id, String[] values) {
        if (values == null || values.length == 0) return null;
        // 校验是否存在
        Specification specification = specificationService.getById(id);
        List<SpecValues> res = new ArrayList<>();
        if (specification != null) {
            // 删除现有规格值
            QueryWrapper<SpecValues> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("spec_id", id);
            baseMapper.delete(queryWrapper);
            // 添加新的规格值
            for (String value : values) {
                SpecValues specValues = new SpecValues();
                specValues.setSpecValue(value);
                specValues.setSpecId(id);
                res.add(specValues);
            }
            saveBatch(res);
        }
        return res;
    }

    @Override
    public List<SpecValues> getSpecValues(List<String> specIds) {
        if (specIds == null || specIds.size() == 0) return null;
        LambdaQueryWrapper<SpecValues> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SpecValues::getSpecId, specIds);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Page<SpecValues> queryByParams(String specId, String specVal, PageVo pageVo) {
        LambdaQueryWrapper<SpecValues> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotEmpty(specId)) {
            queryWrapper.eq(SpecValues::getSpecId, specId);
        }
        if (StrUtil.isNotEmpty(specVal)) {
            queryWrapper.like(SpecValues::getSpecValue, specVal);
        }
        return this.page(PageUtil.initPage(pageVo), queryWrapper);
    }

    @Override
    public SpecValues getSpecValues(String specValue, String specId) {
        LambdaQueryWrapper<SpecValues> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SpecValues::getSpecValue, specValue);
        queryWrapper.eq(SpecValues::getSpecId, specId);

        SpecValues specValues = this.getOne(queryWrapper);
        if (specValues == null) {
            specValues = new SpecValues();
            specValues.setSpecValue(specValue);
            specValues.setSpecId(specId);
            this.save(specValues);
        }
        return specValues;
    }
}
