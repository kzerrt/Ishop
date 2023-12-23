package com.fc.ishop.controller.trade;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dos.trade.Order;
import com.fc.ishop.dto.MemberAddressDto;
import com.fc.ishop.dto.OrderSearchParams;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.order.OrderDetailVo;
import com.fc.ishop.vo.order.OrderSimpleVo;
import com.fc.ishop.web.manager.OrderManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 订单管理
 * @author florence
 * @date 2023/12/19
 */
@RestController
@RequestMapping("/orders")
public class OrderManagerController {

    @Autowired
    private OrderManagerClient orderManagerClient;
    
    //(value = "查询订单列表分页")
    @GetMapping
    public ResultMessage<Page<OrderSimpleVo>> queryMineOrder(OrderSearchParams orderSearchParams) {
        return orderManagerClient.queryMineOrder(orderSearchParams);
    }

    //(value = "订单明细")
    @GetMapping(value = "/{orderSn}")
    public ResultMessage<OrderDetailVo> detail(@PathVariable String orderSn) {
        return orderManagerClient.detail(orderSn);
    }


    //(value = "确认收款")
    @PostMapping(value = "/{orderSn}/pay")
    public ResultMessage<Object> payOrder(@PathVariable String orderSn) {
        return orderManagerClient.payOrder(orderSn);
    }

    //(value = "修改收货人信息")
    @PostMapping(value = "/update/{orderSn}/consignee")
    public ResultMessage<Order> consignee(@NotNull(message = "参数非法") @PathVariable String orderSn,
                                          @Valid MemberAddressDto memberAddressDTO) {
        return orderManagerClient.consignee(orderSn, memberAddressDTO);
    }

    //(value = "修改订单价格")
    @PutMapping(value = "/update/{orderSn}/price")
    public ResultMessage<Order> updateOrderPrice(@PathVariable String orderSn,
                                                 @NotNull(message = "订单价格不能为空") @RequestParam Double price) {
        return orderManagerClient.updateOrderPrice(orderSn, price);
    }


    //(value = "取消订单")
    @PostMapping(value = "/{orderSn}/cancel")
    public ResultMessage<Order> cancel(@PathVariable String orderSn, @RequestParam String reason) {
        return orderManagerClient.cancel(orderSn, reason);
    }
}
