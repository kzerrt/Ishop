package com.fc.ishop.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.fc.ishop.dos.StoreFlow;
import com.fc.ishop.vo.goods.GoodsStatisticsDataVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品统计
 * @author florence
 * @date 2023/12/26
 */
public interface GoodsStatisticsDataMapper extends BaseMapper<StoreFlow> {
    //商品统计
    @Select("SELECT goods_id,goods_name,SUM(final_price) AS price,SUM(num) AS num FROM li_store_flow ${ew.customSqlSegment}")
    List<GoodsStatisticsDataVo> getGoodsStatisticsData(IPage<GoodsStatisticsDataVo> page, @Param(Constants.WRAPPER) Wrapper<GoodsStatisticsDataVo> queryWrapper);

    /*//分类统计
    @Select("SELECT category_id,category_name,SUM(price) AS price,SUM(num) AS num FROM li_store_flow ${ew.customSqlSegment}")
    List<CategoryStatisticsDataVO> getCateGoryStatisticsData(@Param(Constants.WRAPPER) Wrapper<CategoryStatisticsDataVo> queryWrapper);*/
}
