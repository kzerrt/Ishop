package com.fc.ishop.service;

import com.fc.ishop.dos.trade.Order;

/**
 * 订单价格
 * @author florence
 * @date 2023/12/18
 */
public interface OrderPriceService {
    /**
     * 管理员订单付款
     * @param orderSn
     */
    void adminPayOrder(String orderSn);

    /**
     * 修改价格
     * @param orderSn
     * @param price
     * @return
     */
    Order updatePrice(String orderSn, Double price);
}
