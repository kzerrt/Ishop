package com.fc.ishop.web.manager;

import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.category.CategoryBrandVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 管理员 分类品牌管理
 * @author florence
 * @date 2023/12/12
 */
@FeignClient(value = "goods-server", contextId = "categoryBrand")
public interface CategoryBrandManagerClient {

    /**
     * 查询某分类下品牌信息
     */
    @GetMapping("/manager-cb/{categoryId}")
    ResultMessage<List<CategoryBrandVo>> getCategoryBrand(@PathVariable String categoryId);

    /**
     * 保存某分类下的品牌信息
     * @return
     */
    @PostMapping("/manager-cb/save/{categoryId}")
    ResultMessage<Object> saveCategoryBrand(@PathVariable String categoryId, @RequestParam String[] categoryBrands);
}
