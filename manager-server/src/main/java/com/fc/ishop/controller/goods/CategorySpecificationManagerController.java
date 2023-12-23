package com.fc.ishop.controller.goods;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.category.CategorySpecificationVo;
import com.fc.ishop.vo.goods.GoodsSpecValueVo;
import com.fc.ishop.web.manager.CategorySpecificationManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品分类规格接口
 * @author florence
 * @date 2023/12/16
 */
@RestController
@RequestMapping("/goods/category/spec")
public class CategorySpecificationManagerController {
    @Autowired
    private CategorySpecificationManagerClient categorySpecificationManagerClient;

    /**
     * 查询某分类下绑定的规格信息
     */
    @GetMapping(value = "/{categoryId}")
    public List<CategorySpecificationVo> getCategorySpec(@PathVariable String categoryId) {
        return categorySpecificationManagerClient.getCategorySpec(categoryId);
    }

    /**
     * 查询某分类下绑定的规格信息,商品操作使用
     */
    @GetMapping(value = "/goods/{categoryId}")
    public List<GoodsSpecValueVo> getSpec(@PathVariable String categoryId) {
        return categorySpecificationManagerClient.getSpec(categoryId);
    }


    /**
     * 保存某分类下绑定的规格信息
     */
    @PostMapping(value = "/{categoryId}")
    public ResultMessage<Object> saveCategoryBrand(@PathVariable String categoryId,
                                                   @RequestParam String[] categorySpecs) {
        return categorySpecificationManagerClient.saveCategoryBrand(categoryId, categorySpecs);
    }
}
