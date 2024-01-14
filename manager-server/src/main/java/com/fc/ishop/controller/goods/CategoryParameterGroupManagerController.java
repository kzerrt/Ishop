package com.fc.ishop.controller.goods;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.ishop.dos.category.CategoryParameterGroup;
import com.fc.ishop.vo.ParameterGroupVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.web.manager.CategoryParameterGroupManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/13
 */
@RestController
@RequestMapping("/goods/category/parameters")
public class CategoryParameterGroupManagerController {
    @Autowired
    private CategoryParameterGroupManagerClient managerClient;

    /**
     * 查询某分类下绑定的参数信息
     * @param categoryId
     * @return
     */
    @GetMapping(value = "/get//{categoryId}")
    public ResultMessage<List<ParameterGroupVo>> getCategoryParam(@PathVariable String categoryId) {
        return managerClient.getCategoryParam(categoryId);
    }

    /**
     * 保存数据
     */
    @PostMapping
    public ResultMessage<CategoryParameterGroup> saveCategoryParameterGroup(CategoryParameterGroup categoryParameterGroup) {
        return managerClient.saveCategoryParameterGroup(categoryParameterGroup);
    }

    /**
     * 更新数据
     */
    @PutMapping
    public ResultMessage<CategoryParameterGroup> update(CategoryParameterGroup categoryParameterGroup) {
        return managerClient.update(categoryParameterGroup);
    }

    /**
     * 通过id删除参数组
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResultMessage<Object> delAllByIds(@PathVariable String id) {
        return managerClient.delAllByIds(id);
    }
}
