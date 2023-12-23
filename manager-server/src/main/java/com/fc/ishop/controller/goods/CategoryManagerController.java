package com.fc.ishop.controller.goods;

import com.fc.ishop.dos.category.Category;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.category.CategoryVo;
import com.fc.ishop.web.manager.CategoryManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 管理端 商品分类接口
 * @author florence
 * @date 2023/12/12
 */
@RestController
@RequestMapping("/goods/category")
public class CategoryManagerController {
    @Autowired
    private CategoryManagerClient categoryManagerClient;

    /**
     * 获取子分类
     * @param parentId
     * @return
     */
    @GetMapping(value = "/{parentId}/all-children")
    public ResultMessage<List<Category>> list(@PathVariable String parentId) {
        return categoryManagerClient.list(parentId);
    }

    /**
     * 查看所有分类，包括禁用的
     * @return
     */
    @GetMapping(value = "/allChildren")
    public ResultMessage<List<CategoryVo>> list() {
        return categoryManagerClient.list();
    }

    @PostMapping
    public ResultMessage<Category> saveCategory(@Valid Category category) {
        return categoryManagerClient.saveCategory(category);
    }

    @PutMapping
    public ResultMessage<Category> updateCategory(CategoryVo category) {
        return categoryManagerClient.updateCategory(category);
    }

    @DeleteMapping(value = "/{id}")
    public ResultMessage<Category> delAllByIds(@NotNull @PathVariable String id) {

        return categoryManagerClient.delAllByIds(id);
    }

    @PutMapping(value = "/disable/{id}")
    public ResultMessage<Object> disable(@PathVariable String id, @RequestParam Boolean enableOperations) {
        return categoryManagerClient.disable(id, enableOperations);
    }
}
