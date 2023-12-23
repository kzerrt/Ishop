package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.cache.CachePrefix;
import com.fc.ishop.cache.RedisCache;
import com.fc.ishop.dos.category.Category;
import com.fc.ishop.mapper.CategoryMapper;
import com.fc.ishop.service.CategoryService;
import com.fc.ishop.vo.category.CategoryVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author florence
 * @date 2023/12/11
 */
@Service("categoryService")
@Transactional
public class CategoryServiceImpl
        extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    // 所有分类一级缓存
    private static final Map<String /*id*/, CategoryVo> CATEGORY_CACHE = new HashMap<>(16);

    @Override
    public List<Category> dbList(String parentId) {
        return this.list(new LambdaQueryWrapper<Category>().eq(Category::getParentId, parentId));
    }

    /**
     * 管理员获取所有分类
     *
     * @return
     */
    @Override
    public List<CategoryVo> listAllChildren() {
        // 构造分类树
        List<CategoryVo> tree = new LinkedList<>();
        synchronized (CATEGORY_CACHE) {
            if (CATEGORY_CACHE.size() == 0) {
                // 获取所有分类
                List<Category> list = this.list();
                CategoryVo categoryVo;
                for (Category category : list) {
                    categoryVo = new CategoryVo(category);
                    CATEGORY_CACHE.put(category.getId(), categoryVo);
                    if ("0".equals(category.getParentId())) {
                        tree.add(categoryVo);
                    } else {
                        CategoryVo parent = CATEGORY_CACHE.get(category.getParentId());
                        if (parent != null) {
                            parent.listChildren().add(categoryVo);
                        }
                    }
                }
            } else {
                // 获取顶级分类
                QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("parent_id", 0);
                List<Category> categories = baseMapper.selectList(queryWrapper);
                // 从缓存中获取顶级分类
                for (Category category : categories) {
                    tree.add(CATEGORY_CACHE.get(category.getId()));
                }
            }
        }
        tree.sort((o1, o2) -> o1.getSortOrder().compareTo(o2.getSortOrder()));
        return tree;
    }

    @Override
    public void saveCategory(Category category) {
        if (category.getParentId() != null && !category.getParentId().equals("0")) {
            Category parentCategory = this.getById(category.getParentId());
            category.setDeleteFlag(parentCategory.getDeleteFlag());
        }
        //this.save(category);
        removeCache(category, this::save);
    }

    @Override
    public void updateCategoryStatus(String id, Boolean enableOperations) {

        // 获取该id下的所哟子分类id
        List<String> allChildrenById = findAllChildrenById(id);
        // 没必要获取子分类，在查询时将子分类删除即可
        LambdaUpdateWrapper<Category> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(Category::getDeleteFlag, enableOperations);
        lambdaUpdateWrapper.in(Category::getId, allChildrenById);
        //this.update(lambdaUpdateWrapper);
        removeCache(lambdaUpdateWrapper, this::update);
    }

    @Override
    public void updateCategory(CategoryVo category) {
        UpdateWrapper<Category> updateWrapper = new UpdateWrapper<>();
        // todo 修改分类信息 夫分类的状态修改，子分类是否修改
        updateWrapper.eq("id", category.getId())
                .set("name", category.getName())
                .set("image", category.getImage())
                .set("sort_order", category.getSortOrder())
                .set("delete_flag", category.getDeleteFlag())
                .set("commission_rate", category.getCommissionRate());
        removeCache(updateWrapper, this::update);
    }

    @Override
    public void deleteById(String id) {
        removeCache(id, this::removeById);
    }

    @Override
    public List<Category> firstCategory() {
        QueryWrapper<Category> query = Wrappers.query();
        query.eq("level", 0);
        return baseMapper.selectList(query);
    }

    /**
     * 根据某分类id 查询所有子分类的id
     *
     * @param id
     * @return
     */
    private List<String> findAllChildrenById(String id) {
        if (id == null) return null;
        // 从缓存 获取id的分类信息
        CategoryVo categoryVo = null;
        synchronized (CATEGORY_CACHE) {
            if (CATEGORY_CACHE.size() != 0) {
                categoryVo = CATEGORY_CACHE.get(id);
            }
        }
        List<String> allIds = new LinkedList<>();
        if (categoryVo == null) {
            List<CategoryVo> categoryVos = listAllChildren();

            for (CategoryVo category : categoryVos) {
                if (id.equals(category.getId())) {
                    // 将本身id添加
                    allIds.add(id);
                    findChildrenByParent(category, allIds);
                    break;
                }
            }
        } else {
            allIds.add(categoryVo.getId());
            findChildrenByParent(categoryVo, allIds);
        }
        return allIds;
    }

    /**
     * 根据父分类vo获取子分类id
     *
     * @param categoryVo
     * @param children
     */
    private void findChildrenByParent(CategoryVo categoryVo, List<String> children) {
        List<CategoryVo> tmp = categoryVo.getChildren();
        if (tmp == null || tmp.size() == 0) {
            return;
        }
        for (CategoryVo child : tmp) {
            children.add(child.getId());
            // 递归将子分类中的分类获取
            findChildrenByParent(child, children);
        }
    }

    private <T> void removeCache(T Wrapper, Consumer<T> consumer) {
        // 延迟双删缓存
        RedisCache.getCache().remove(CachePrefix.CATEGORY.getPrefix() + "tree");
        consumer.accept(Wrapper);
        // 将缓存清除
        synchronized (CATEGORY_CACHE) {
            CATEGORY_CACHE.clear();
        }
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                RedisCache.getCache().remove(CachePrefix.CATEGORY.getPrefix() + "tree");
            }
            RedisCache.getCache().remove(CachePrefix.CATEGORY.getPrefix() + "tree");
        });
    }
}
