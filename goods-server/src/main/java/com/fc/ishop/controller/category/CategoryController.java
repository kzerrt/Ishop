package com.fc.ishop.controller.category;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.ishop.dos.category.Category;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.service.CategoryService;
import com.fc.ishop.service.GoodsService;
import com.fc.ishop.vo.category.CategoryVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.web.manager.CategoryManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/11
 */
@RestController
public class CategoryController implements CategoryManagerClient {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private GoodsService goodsService;

    @Override
    public ResultMessage<List<Category>> list(String parentId) {
        return ResultUtil.data(this.categoryService.dbList(parentId));
    }

    @Override
    public ResultMessage<List<CategoryVo>> list() {
        return ResultUtil.data(categoryService.listAllChildren());
    }

    @Override
    public ResultMessage<Category> saveCategory(@Valid Category category) {
        // 查看是否存在重复名称
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Category::getName, category.getName());
        int count = categoryService.count(lambdaQueryWrapper);
        if (count != 0) {
            throw new ServiceException(ResultCode.CATEGORY_NOT_EXIST);
        }
        // 非顶级分类
        if (category.getParentId() != null && !"0".equals(category.getParentId())) {
            Category parent = categoryService.getById(category.getParentId());
            if (parent == null) {
                throw new ServiceException(ResultCode.CATEGORY_PARENT_NOT_EXIST);
            }
            if (category.getLevel() >= 4) {
                throw new ServiceException(ResultCode.CATEGORY_BEYOND_THREE);
            }
        }
        categoryService.saveCategory(category);
        return ResultUtil.data(category);
    }

    @Override
    public ResultMessage<Category> updateCategory(CategoryVo category) {
        Category tmp = categoryService.getById(category.getId());
        if (tmp == null) {
            throw new ServiceException(ResultCode.CATEGORY_PARENT_NOT_EXIST);
        }
        // 查看是否存在有相同名称
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", category.getName());
        queryWrapper.ne("id", category.getId());
        int count = categoryService.count(queryWrapper);
        if (count != 0) {
            throw new ServiceException(ResultCode.CATEGORY_NAME_IS_EXIST);
        }
        categoryService.updateCategory(category);
        return ResultUtil.data(category);
    }

    @Override
    public ResultMessage<Category> delAllByIds(@NotNull String id) {
        // 查找是否含有子分类
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        int count = categoryService.count(queryWrapper);
        if (count != 0) {
            throw new ServiceException(ResultCode.CATEGORY_HAS_CHILDREN);
        }
        // 查询某商品分类的商品数量
        int time = goodsService.getGoodsCountByCategory(id);
        if (time > 0) {
            throw new ServiceException(ResultCode.CATEGORY_HAS_GOODS);
        }
        categoryService.deleteById(id);
        return ResultUtil.success();
    }

    @Override
    public ResultMessage<Object> disable(String id, Boolean enableOperations) {
        Category category = categoryService.getById(id);
        if (category == null) {
            throw new ServiceException(ResultCode.CATEGORY_NOT_EXIST);
        }
        categoryService.updateCategoryStatus(id, enableOperations);
        return ResultUtil.success();
    }
}
