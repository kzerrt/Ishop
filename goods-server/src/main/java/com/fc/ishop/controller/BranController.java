package com.fc.ishop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dos.Brand;
import com.fc.ishop.dto.BrandPageDto;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.service.BrandService;
import com.fc.ishop.vo.BrandVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.web.manager.BrandManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/11
 */
@RestController
public class BranController implements BrandManagerClient {
    @Autowired
    private BrandService brandService;
    @Override
    public ResultMessage<Brand> get(@NotNull String id) {
        return ResultUtil.data(brandService.getById(id));
    }

    @Override
    public List<Brand> getAll() {
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("delete_flag", 0);
        return brandService.list(queryWrapper);
    }

    @Override
    public ResultMessage<Page<Brand>> getByPage(BrandPageDto page) {
        return ResultUtil.data(brandService.getBrandsByPage(page));
    }

    @Override
    public ResultMessage<BrandVo> save(BrandVo brand) {
        brandService.saveBrand(brand);
        return ResultUtil.data(brand);
    }

    @Override
    public ResultMessage<BrandVo> update(String id, BrandVo brand) {
        brand.setId(id);
        brandService.updateBrand(brand);
        return ResultUtil.data(brand);
    }

    @Override
    public ResultMessage<Object> disable(String id, Boolean disable) {
        brandService.brandDisable(id, disable);
        return ResultUtil.success();
    }

    @Override
    public ResultMessage<Object> delAllByIds(List<String> ids) {
        brandService.deleteByIds(ids);
        return ResultUtil.success();
    }
}
