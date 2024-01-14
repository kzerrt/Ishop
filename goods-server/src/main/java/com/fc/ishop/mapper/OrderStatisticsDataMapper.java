package com.fc.ishop.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.fc.ishop.dos.StoreFlow;
import com.fc.ishop.vo.order.OrderStatisticsDataVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/26
 */
public interface OrderStatisticsDataMapper extends BaseMapper<StoreFlow> {
    @Select("SELECT DATE_FORMAT(create_time,'%Y-%m-%d') AS create_time,sum(flow_price) AS price FROM i_order " +
            " ${ew.customSqlSegment}")
    List<OrderStatisticsDataVo> getOrderStatisticsData(@Param(Constants.WRAPPER) QueryWrapper queryWrapper);
}
