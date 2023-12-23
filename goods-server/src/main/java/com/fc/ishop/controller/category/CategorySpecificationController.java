package com.fc.ishop.controller.category;

import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.service.CategorySpecificationService;
import com.fc.ishop.service.SpecificationService;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.category.CategorySpecificationVo;
import com.fc.ishop.vo.goods.GoodsSpecValueVo;
import com.fc.ishop.web.manager.CategorySpecificationManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/16
 */
@RestController
public class CategorySpecificationController implements CategorySpecificationManagerClient {

    @Autowired
    private CategorySpecificationService categorySpecificationService;
    /**
     * 规格
     */
    @Autowired
    private SpecificationService specificationService;

    @Override
    public List<CategorySpecificationVo> getCategorySpec(String categoryId) {
        return categorySpecificationService.getCategorySpecList(categoryId);
    }

    @Override
    public List<GoodsSpecValueVo> getSpec(String categoryId) {
        return specificationService.getGoodsSpecValue(categoryId);
    }

    @Override
    public ResultMessage<Object> saveCategoryBrand(String categoryId, String[] categorySpecs) {
        categorySpecificationService.saveSpecification(categoryId, categorySpecs);
        return ResultUtil.success();
    }
}
