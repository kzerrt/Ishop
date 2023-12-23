package com.fc.ishop.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dos.trade.Order;
import com.fc.ishop.vo.order.OrderSimpleVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author florence
 * @date 2023/12/18
 */
public interface OrderMapper extends BaseMapper<Order> {

    Page<OrderSimpleVo> queryByParams(Page<Object> initPage,@Param(Constants.WRAPPER) QueryWrapper<Object> queryWrapper);
}
