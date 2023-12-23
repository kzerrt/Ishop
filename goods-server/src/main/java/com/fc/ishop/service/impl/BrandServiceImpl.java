package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.Brand;
import com.fc.ishop.dto.BrandPageDto;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.mapper.BrandMapper;
import com.fc.ishop.service.BrandService;
import com.fc.ishop.service.CategoryBrandService;
import com.fc.ishop.utils.PageUtil;
import com.fc.ishop.vo.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/11
 */
@Service("brandService")
public class BrandServiceImpl
        extends ServiceImpl<BrandMapper, Brand> implements BrandService {
    @Autowired
    private CategoryBrandService categoryBrandService;
    @Override
    public Page<Brand> getBrandsByPage(BrandPageDto page) {
        LambdaQueryWrapper<Brand> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (page.getName() != null) {
            lambdaQueryWrapper.like(Brand::getName, page.getName());
        }
        return this.page(PageUtil.initPage(page), lambdaQueryWrapper);
    }

    @Override
    public void saveBrand(BrandVo brand) {
        if (getOne(new LambdaQueryWrapper<Brand>().eq(Brand::getName, brand.getName())) != null) {
            throw new ServiceException("品牌名称重复！");
        }
        try {
            this.save(brand);
        } catch (Exception e) {
            throw new ServiceException(ResultCode.BRAND_SAVE_ERROR);
        }

    }

    @Override
    public void updateBrand(BrandVo brand) {
        checkExist(brand.getId());
        if (getOne(new LambdaQueryWrapper<Brand>().eq(Brand::getName, brand.getName()).ne(Brand::getId, brand.getId())) != null) {
            throw new ServiceException("品牌名称重复！");
        }
        try {
            this.updateById(brand);
        } catch (Exception e) {
            throw new ServiceException(ResultCode.BRAND_UPDATE_ERROR);
        }

    }

    @Override
    public void brandDisable(String id, boolean disable) {
        Brand brand = checkExist(id);
        if (Boolean.TRUE.equals(disable) && !categoryBrandService.getCategoryBrandListByBrandId(id).isEmpty()) {
            throw new ServiceException("当前品牌下存在分类不可禁用");
        }
        brand.setDeleteFlag(disable);
        try {
            updateById(brand);
        } catch (Exception e) {
            throw new ServiceException(ResultCode.BRAND_UPDATE_ERROR);
        }

    }

    @Override
    public void deleteByIds(List<String> ids) {
        UpdateWrapper<Brand> updateWrapper = Wrappers.update();
        updateWrapper.in("id", ids);
        updateWrapper.set("delete_flag", 1);
        try {
            this.update(updateWrapper);
        } catch (Exception e) {
            throw new ServiceException(ResultCode.BRAND_DELETE_ERROR);
        }
    }

    private Brand checkExist(String id) {
        Brand brand = getById(id);
        if (brand == null) {
            log.error("品牌id   " + id + " 不存在");
            throw new ServiceException();
        }
        return brand;
    }

}
