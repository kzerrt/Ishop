package com.fc.ishop.controller.trade;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dos.trade.Order;
import com.fc.ishop.dto.MemberAddressDto;
import com.fc.ishop.dto.OrderSearchParams;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.service.OrderPriceService;
import com.fc.ishop.service.OrderService;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.order.OrderDetailVo;
import com.fc.ishop.vo.order.OrderSimpleVo;
import com.fc.ishop.web.manager.OrderManagerClient;
import com.fc.ishop.web.manager.statistic.OrderStatisticClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单
 * @author florence
 * @date 2023/12/18
 */
@RestController
public class OrderController implements OrderManagerClient {
    // 订单
    @Autowired
    private OrderService orderService;

    // 订单金额
    private OrderPriceService orderPriceService;
    @Override
    public ResultMessage<Page<OrderSimpleVo>> queryMineOrder(OrderSearchParams orderSearchParams) {
        return ResultUtil.data(orderService.queryByParams(orderSearchParams));
    }

    @Override
    public ResultMessage<OrderDetailVo> detail(String orderSn) {
        return ResultUtil.data(orderService.queryDetail(orderSn));
    }

    @Override
    public ResultMessage<Object> payOrder(String orderSn) {
        orderPriceService.adminPayOrder(orderSn);
        return ResultUtil.success();
    }

    @Override
    public ResultMessage<Order> consignee(String orderSn, MemberAddressDto memberAddressDto) {
        return ResultUtil.data(orderService.updateConsignee(orderSn, memberAddressDto));
    }

    @Override
    public ResultMessage<Order> updateOrderPrice(String orderSn, Double price) {
        return ResultUtil.data(orderPriceService.updatePrice(orderSn, price));
    }

    @Override
    public ResultMessage<Order> cancel(String orderSn, String reason) {
        return ResultUtil.data(orderService.cancel(orderSn, reason));
    }
}
