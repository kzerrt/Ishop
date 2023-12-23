package com.fc.ishop.dto;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.ishop.utils.StringUtils;
import lombok.Data;

/**
 * 规格搜索参数
 * @author florence
 * @date 2023/12/15
 */
@Data
public class SpecificationSearchParams {
    /**
     * 规格名
     */
    private String specName;
    /**
     *绑定分类
     */
    private String categoryPath;
    /**
     *未删除
     */
    private Boolean deleteFlag;

    public <T> QueryWrapper<T> queryWrapper() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(specName), "spec_name", specName);
        queryWrapper.eq(deleteFlag != null, "delete_flag", deleteFlag);
        return queryWrapper;
    }
}
