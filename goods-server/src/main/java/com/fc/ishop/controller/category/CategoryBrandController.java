package com.fc.ishop.controller.category;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.ishop.dos.category.CategoryBrand;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.service.CategoryBrandService;
import com.fc.ishop.vo.category.CategoryBrandVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.web.manager.CategoryBrandManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/12
 */
@RestController
public class CategoryBrandController implements CategoryBrandManagerClient {
    @Autowired
    private CategoryBrandService categoryBrandService;
    @Override
    public ResultMessage<List<CategoryBrandVo>> getCategoryBrand(String categoryId) {
        return ResultUtil.data(categoryBrandService.getCategoryBrandList(categoryId));
    }

    @Override
    public ResultMessage<Object> saveCategoryBrand(String categoryId, String[] categoryBrands) {
        //删除分类品牌绑定信息
        this.categoryBrandService.remove(new QueryWrapper<CategoryBrand>().eq("category_id", categoryId));
        //绑定品牌信息
        List<CategoryBrand> list = new ArrayList<>(categoryBrands.length);
        for (String categoryBrand : categoryBrands) {
            list.add(new CategoryBrand(categoryId, categoryBrand));
        }
        categoryBrandService.saveBatch(list);
        return ResultUtil.success();
    }
}
