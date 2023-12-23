package com.fc.ishop.controller.goods;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.category.CategoryBrandVo;
import com.fc.ishop.web.manager.CategoryBrandManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/12
 */
@RestController
@RequestMapping("/category/brand")
public class CategoryBrandManagerController {
    @Autowired
    private CategoryBrandManagerClient categoryBrandManagerClient;

    /**
     * 查询某分类下绑定的品牌信息
     * @param categoryId
     * @return
     */
    @GetMapping(value = "/{categoryId}")
    public ResultMessage<List<CategoryBrandVo>> getCategoryBrand(@PathVariable String categoryId) {
        return categoryBrandManagerClient.getCategoryBrand(categoryId);
    }

    /**
     * 保存某分类下绑定的品牌信息
     * @param categoryId
     * @param categoryBrands
     * @return
     */
    @PostMapping(value = "/{categoryId}")
    public ResultMessage<Object> saveCategoryBrand(@PathVariable String categoryId, @RequestParam String[] categoryBrands) {

        return categoryBrandManagerClient.saveCategoryBrand(categoryId, categoryBrands);
    }
}
