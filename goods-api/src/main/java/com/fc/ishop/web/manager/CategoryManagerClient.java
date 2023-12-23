package com.fc.ishop.web.manager;

import com.fc.ishop.dos.category.Category;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.category.CategoryVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/11
 */
@FeignClient(value = "goods-server", contextId = "category")
public interface CategoryManagerClient {
    /**
     * 查询某分类下的子分类
     * @param parentId
     * @return
     */
    @GetMapping("/manager-c/{parentId}/allChildren")
    ResultMessage<List<Category>> list(@PathVariable String parentId);

    /**
     * 获取所有分类
     * @return
     */
    @GetMapping("/manager-c/category/allChildren")
    ResultMessage<List<CategoryVo>> list();

    /**
     * 添加分类
     * @param category
     * @return
     */
    @PostMapping("/manager-c/addCategory")
    ResultMessage<Category> saveCategory(@RequestBody Category category);

    @PostMapping("/manager-c/updateCategory")
    ResultMessage<Category> updateCategory(@RequestBody CategoryVo category);

    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/manager-c/{id}")
    ResultMessage<Category> delAllByIds(@PathVariable String id);

    /**
     * 修改状态
     * @param id
     * @param enableOperations
     * @return
     */
    @PutMapping("/manager-c/disable/{id}")
    ResultMessage<Object> disable(@PathVariable String id, @RequestParam Boolean enableOperations);
}
