package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.StoreDetail;
import com.fc.ishop.dos.category.Category;
import com.fc.ishop.mapper.StoreDetailMapper;
import com.fc.ishop.service.CategoryService;
import com.fc.ishop.service.StoreDetailService;
import com.fc.ishop.vo.StoreDetailVo;
import com.fc.ishop.vo.StoreManagementCategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/18
 */
@Service("storeDetailService")
public class StoreDetailServiceImpl
        extends ServiceImpl<StoreDetailMapper, StoreDetail> implements StoreDetailService {

    @Autowired
    private CategoryService categoryService;
    @Override
    public StoreDetailVo getStoreDetail(String storeId) {
        return baseMapper.getDetail(storeId);
    }

    @Override
    public List<StoreManagementCategoryVo> goodsManagementCategory(String storeId) {
        //获取顶部分类列表
        List<Category> categoryList = categoryService.firstCategory();
        // 获取店铺信息
        StoreDetail storeDetail = baseMapper.selectById(storeId);
        // 获取店铺分类
        String[] storeCategoryList = storeDetail.getGoodsManagementCategory().split(",");

        List<StoreManagementCategoryVo> list = new ArrayList<>();
        for (Category category : categoryList) {
            StoreManagementCategoryVo storeManagementCategoryVO = new StoreManagementCategoryVo(category);
            for (String storeCategory:storeCategoryList) {
                if (storeCategory.equals(category.getId())) {
                    storeManagementCategoryVO.setSelected(true);
                }
            }
            list.add(storeManagementCategoryVO);
        }
        return list;
    }
}
