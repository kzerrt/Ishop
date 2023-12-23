package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.trade.OrderItem;
import com.fc.ishop.mapper.OrderItemMapper;
import com.fc.ishop.service.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/18
 */
@Service("orderItemService")
public class OrderItemServiceImpl
        extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {
    @Override
    public List<OrderItem> getByOrderSn(String sn) {
        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_sn", sn);
        return baseMapper.selectList(queryWrapper);
    }
}
