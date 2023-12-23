package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.trade.Order;
import com.fc.ishop.dto.MemberAddressDto;
import com.fc.ishop.dto.OrderMessage;
import com.fc.ishop.dto.OrderSearchParams;
import com.fc.ishop.vo.order.OrderDetailVo;
import com.fc.ishop.vo.order.OrderSimpleVo;

/**
 * @author florence
 * @date 2023/12/18
 */
public interface OrderService extends IService<Order> {
    /**
     * 根据sn查询
     *
     * @param orderSn 订单编号
     * @return 订单信息
     */
    Order getBySn(String orderSn);
    /**
     * 查询订单列表分页
     * @param orderSearchParams
     * @return
     */
    Page<OrderSimpleVo> queryByParams(OrderSearchParams orderSearchParams);

    OrderDetailVo queryDetail(String orderSn);

    void sendUpdateStatusMessage(OrderMessage orderMessage);

    /**
     * 更新订单地址
     * @param orderSn
     * @param memberAddressDto
     * @return
     */
    Order updateConsignee(String orderSn, MemberAddressDto memberAddressDto);

    Order cancel(String orderSn, String reason);
}
