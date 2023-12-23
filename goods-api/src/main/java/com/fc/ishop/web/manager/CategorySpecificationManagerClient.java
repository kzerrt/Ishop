package com.fc.ishop.web.manager;

import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.category.CategorySpecificationVo;
import com.fc.ishop.vo.goods.GoodsSpecValueVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 商品分类规格
 * @author florence
 * @date 2023/12/16
 */
@FeignClient(value = "goods-server", contextId = "categorySpec")
public interface CategorySpecificationManagerClient {
    /**
     * 查询分类下的规格信息
     * @param categoryId
     * @return
     */
    @GetMapping("/manager-cs/{categoryId}")
    List<CategorySpecificationVo> getCategorySpec(@PathVariable String categoryId);

    /**
     * 查询某分类下绑定的规格信息,商品操作使用
     * @param categoryId
     * @return
     */
    @GetMapping("/manager-cs/get/{categoryId}")
    List<GoodsSpecValueVo> getSpec(@PathVariable String categoryId);

    /**
     * 保存分类下的规格信息
     * @param categoryId
     * @param categorySpecs
     * @return
     */
    @PostMapping("/manager-cs/save/{categoryId}")
    ResultMessage<Object> saveCategoryBrand(@PathVariable String categoryId,
                                            @RequestParam String[] categorySpecs);
}
