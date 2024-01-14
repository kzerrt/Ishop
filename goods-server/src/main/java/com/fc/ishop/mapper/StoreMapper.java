package com.fc.ishop.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dos.Store;
import com.fc.ishop.vo.StoreVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author florence
 * @date 2023/12/18
 */
public interface StoreMapper extends BaseMapper<Store> {
    /**
     * 获取店铺分页列表
     *
     * @param page         分页
     * @param queryWrapper 查询条件
     * @return 店铺VO分页列表
     */
    @Select("select s.* from i_store as s ${ew.customSqlSegment}")
    Page<StoreVo> getStoreList(IPage<StoreVo> page, @Param(Constants.WRAPPER) Wrapper<StoreVo> queryWrapper);
}
