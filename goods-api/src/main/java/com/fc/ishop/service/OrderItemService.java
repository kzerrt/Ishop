package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.trade.OrderItem;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/18
 */
public interface OrderItemService extends IService<OrderItem> {
    List<OrderItem> getByOrderSn(String sn);
}
