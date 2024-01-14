package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.category.Category;
import com.fc.ishop.vo.category.CategoryVo;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/11
 */
public interface CategoryService extends IService<Category> {
    /**
     * 管理端获取所有分类
     * 即获取的对象不管是否删除，都要展示，而且不从缓存获取，保证内容是最新的
     *
     * @return 商品分类列表
     */
    List<Category> dbList(String parentId);
    /**
     * 查询所有的分类，父子关系 数据库获取
     *
     * @return 所有的分类，父子关系
     */
    List<CategoryVo> listAllChildren();

    void saveCategory(Category category);

    /**
     * 修改分类状态
     * @param id
     * @param enableOperations
     */
    void updateCategoryStatus(String id, Boolean enableOperations);

    void updateCategory(CategoryVo category);

    void deleteById(String id);

    /**
     * 获取顶部分类
     * @return
     */
    List<Category> firstCategory();


}
