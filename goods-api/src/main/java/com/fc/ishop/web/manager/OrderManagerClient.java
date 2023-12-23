package com.fc.ishop.web.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dos.trade.Order;
import com.fc.ishop.dto.MemberAddressDto;
import com.fc.ishop.dto.OrderSearchParams;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.order.OrderDetailVo;
import com.fc.ishop.vo.order.OrderSimpleVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author florence
 * @date 2023/12/18
 */
@FeignClient(value = "goods-server", contextId = "order")
public interface OrderManagerClient {
    /**
     * 查看订单分页列表
     * @param orderSearchParams
     * @return
     */
    @PostMapping("/manager-o/query")
    ResultMessage<Page<OrderSimpleVo>> queryMineOrder(@RequestBody OrderSearchParams orderSearchParams);

    /**
     * 订单明细
     * @param orderSn 订单编号
     * @return
     */
    @GetMapping("/manager-o/detail/{orderSn}")
    ResultMessage<OrderDetailVo> detail(@PathVariable String orderSn);

    /**
     * 确认收款
     * @param orderSn
     * @return
     */
    @GetMapping("/manager-o/pay/{orderSn}")
    ResultMessage<Object> payOrder(@PathVariable String orderSn);

    /**
     * 修改订单地址
     */
    @PostMapping("/manager-o/update/address/{orderSn}")
    ResultMessage<Order> consignee(@PathVariable String orderSn,
                                   @RequestBody MemberAddressDto memberAddressDto);

    /**
     * 修改订单价格
     */
    @PostMapping("/manager-o/update/price/{orderSn}")
    ResultMessage<Order> updateOrderPrice(@PathVariable String orderSn,
                                           @RequestParam Double price);

    /**
     * 取消订单
     */
    @PostMapping("/manager-o/cancel/{orderSn}")
    ResultMessage<Order> cancel(@PathVariable String orderSn, @RequestParam String reason);
}
