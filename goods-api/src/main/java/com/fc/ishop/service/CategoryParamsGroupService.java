package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.category.CategoryParameterGroup;
import com.fc.ishop.vo.ParameterGroupVo;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/12
 */
public interface CategoryParamsGroupService extends IService<CategoryParameterGroup> {
    /**
     * 查询分类绑定参数组信息
     *
     * @param categoryId 分类id
     * @return 参数组列表
     */
    List<CategoryParameterGroup> getCategoryGroup(String categoryId);
    /**
     * 查询分类绑定参数集合
     *
     * @param categoryId 分类Id
     * @return 分类参数
     */
    List<ParameterGroupVo> getCategoryParams(String categoryId);
}
