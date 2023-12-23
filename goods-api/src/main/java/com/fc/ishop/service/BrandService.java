package com.fc.ishop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.Brand;
import com.fc.ishop.dto.BrandPageDto;
import com.fc.ishop.vo.BrandVo;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/11
 */
public interface BrandService extends IService<Brand> {
    /**
     * 多条件分页查询数据
     * @param page
     * @return
     */
    Page<Brand> getBrandsByPage(BrandPageDto page);

    void saveBrand(BrandVo brand);

    void updateBrand(BrandVo brand);

    /**
     * 修改品牌状态
     * @param id
     * @param disable
     */
    void brandDisable(String id, boolean disable);

    void deleteByIds(List<String> ids);
}
