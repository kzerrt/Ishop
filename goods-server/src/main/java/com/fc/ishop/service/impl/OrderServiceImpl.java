package com.fc.ishop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.trade.Order;
import com.fc.ishop.dos.trade.OrderItem;
import com.fc.ishop.dto.MemberAddressDto;
import com.fc.ishop.dto.OrderMessage;
import com.fc.ishop.dto.OrderSearchParams;
import com.fc.ishop.enums.OrderStatusEnum;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.mapper.OrderItemMapper;
import com.fc.ishop.mapper.OrderMapper;
import com.fc.ishop.service.OrderService;
import com.fc.ishop.util.OperationalJudgment;
import com.fc.ishop.utils.DateUtil;
import com.fc.ishop.utils.PageUtil;
import com.fc.ishop.utils.StringUtils;
import com.fc.ishop.vo.order.OrderDetailVo;
import com.fc.ishop.vo.order.OrderSimpleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author florence
 * @date 2023/12/18
 */
@Service("orderService")
public class OrderServiceImpl
        extends ServiceImpl<OrderMapper, Order> implements OrderService {
    private final String ORDER_SN_COLUMN = "order_sn";
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Override
    public Order getBySn(String orderSn) {
        QueryWrapper<Order> orderWrapper = new QueryWrapper<>();
        orderWrapper.eq("sn", orderSn);
        return baseMapper.selectOne(orderWrapper);
    }

    @Override
    public Page<OrderSimpleVo> queryByParams(OrderSearchParams orderSearchParams) {
        return this.baseMapper.queryByParams(PageUtil.initPage(orderSearchParams), orderSearchParams.queryWrapper());
    }

    @Override
    public OrderDetailVo queryDetail(String orderSn) {
        // 获取订单
        Order order = getBySn(orderSn);
        if (order == null) {
            throw new ServiceException(ResultCode.ORDER_NOT_EXIST);
        }
        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ORDER_SN_COLUMN, orderSn);
        // 查看订单信息
        List<OrderItem> orderItems = orderItemMapper.selectList(queryWrapper);
        return new OrderDetailVo(order, orderItems);
    }

    @Override
    public void sendUpdateStatusMessage(OrderMessage orderMessage) {
        // todo 发送订单状态修改消息
    }

    @Override
    public Order updateConsignee(String orderSn, MemberAddressDto memberAddressDto) {
        Order order = OperationalJudgment.judgment(this.getBySn(orderSn));
        BeanUtil.copyProperties(memberAddressDto, order);// 更新地址操作
        this.updateById(order);
        return order;
    }

    @Override
    public Order cancel(String orderSn, String reason) {
        Order order = OperationalJudgment.judgment(this.getBySn(orderSn));
        if (StringUtils.equalsAny(order.getOrderStatus(),
                OrderStatusEnum.UNDELIVERED.name(),
                OrderStatusEnum.UNPAID.name(),
                OrderStatusEnum.PAID.name())) {
            order.setOrderStatus(OrderStatusEnum.CANCELLED.name());
            order.setCancelReason(reason);
            //修改订单
            this.updateById(order);
            orderStatusMessage(order);
            return order;
        } else {
            throw new ServiceException("当前订单状态不可取消");
        }
    }

    /**
     * 订单状态变更
     * @param order
     */
    private void orderStatusMessage(Order order) {
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setOrderSn(order.getSn());
        orderMessage.setNewStatus(OrderStatusEnum.valueOf(order.getOrderStatus()));
        this.sendUpdateStatusMessage(orderMessage);
    }

    // ****************************         统计              *****************************

    @Override
    public Integer orderNum(String orderStatus) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(orderStatus)) {
            queryWrapper.eq(Order::getOrderStatus, orderStatus);
        }

        return this.count(queryWrapper);
    }

    @Override
    public Map<String, Object> getOrderStatisticPrice() {
        QueryWrapper<Order> query = Wrappers.query();
        query.eq("order_status",OrderStatusEnum.PAID.name());
        // 大于今天林晨
        query.gt("create_time", DateUtil.startOfTodDay());
        query.select("SUM(flow_price) AS price , COUNT(0) AS num");
        return this.getMap(query);
    }
}
