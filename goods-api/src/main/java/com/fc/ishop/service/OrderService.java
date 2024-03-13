package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.trade.Order;
import com.fc.ishop.dto.MemberAddressDto;
import com.fc.ishop.dto.OrderMessage;
import com.fc.ishop.dto.OrderSearchParams;
import com.fc.ishop.vo.order.OrderDetailVo;
import com.fc.ishop.vo.order.OrderSimpleVo;

import java.util.Map;

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
    /**
     * 发送更新订单状态的信息
     *
     * @param orderMessage 订单传输信息
     */
    void sendUpdateStatusMessage(OrderMessage orderMessage);

    /**
     * 更新订单地址
     * @param orderSn
     * @param memberAddressDto
     * @return
     */
    Order updateConsignee(String orderSn, MemberAddressDto memberAddressDto);

    Order cancel(String orderSn, String reason);
    //**********************************     统计         ************************
    /**
     * 统计订单数量
     * @param orderStatus
     * @return
     */
    Integer orderNum(String orderStatus);

    /**
     * 获取今日交易额
     * @return
     */
    Map<String, Object> getOrderStatisticPrice();

}
