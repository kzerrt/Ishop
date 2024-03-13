package com.fc.ishop.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fc.ishop.dos.trade.Order;
import com.fc.ishop.dos.trade.OrderItem;
import com.fc.ishop.dto.OrderMessage;
import com.fc.ishop.dto.PriceDetailDto;
import com.fc.ishop.enums.OrderStatusEnum;
import com.fc.ishop.enums.PayStatusEnum;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.mapper.TradeMapper;
import com.fc.ishop.service.OrderItemService;
import com.fc.ishop.service.OrderPriceService;
import com.fc.ishop.service.OrderService;
import com.fc.ishop.util.CurrencyUtil;
import com.fc.ishop.util.OperationalJudgment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/18
 */
@Service("orderPriceService")
public class OrderPriceServiceImpl implements OrderPriceService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    // 交易数据层
    @Autowired
    private TradeMapper tradeMapper;
    @Override
    public void adminPayOrder(String orderSn) {

        Order order = OperationalJudgment.judgment(orderService.getBySn(orderSn));

        // 避免多次点击导致账单重复提交
        OperationalJudgment.judgment(orderService.getBySn(orderSn));
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setOrderSn(orderSn);
        orderMessage.setNewStatus(OrderStatusEnum.PAID);
        orderService.sendUpdateStatusMessage(orderMessage);
    }

    @Override
    public Order updatePrice(String orderSn, Double orderPrice) {
        //修改订单金额
        Order order = updateOrderPrice(orderSn, orderPrice);
        //修改订单货物金额
        updateOrderItemPrice(order);
        //修改交易金额
        tradeMapper.updateTradePrice(order.getTradeSn());
        return order;
    }
    /**
     * 修改订单价格
     * 1.判定订单是否支付
     * 2.记录订单原始价格信息
     * 3.计算修改的订单金额
     * 4.修改订单价格
     * 5.保存订单信息
     *
     * @param orderSn    订单编号
     * @param orderPrice 修改订单金额
     */
    private Order updateOrderPrice(String orderSn, Double orderPrice) {
        Order order = OperationalJudgment.judgment(orderService.getBySn(orderSn));
        //判定是否支付
        if (order.getPayStatus().equals(PayStatusEnum.PAID.name())) {
            throw new ServiceException(ResultCode.ORDER_UPDATE_PRICE_ERROR);
        }
        orderService.updateById(order);
        return order;
    }

    /**
     * 修改订单货物金额
     * 1.计算订单货物金额在订单金额中的百分比
     * 2.订单货物金额=订单修改后金额*订单货物百分比
     * 3.订单货物修改价格=订单货物原始价格-订单货物修改后金额
     * 4.修改平台佣金
     * 5.订单实际金额=修改后订单金额-平台佣金-分销提佣
     *
     * @param order 订单
     */
    private void updateOrderItemPrice(Order order) {
        List<OrderItem> orderItems = orderItemService.getByOrderSn(order.getSn());
        if (orderItems == null || orderItems.size() == 0) return;
        for (OrderItem orderItem : orderItems) {
            //获取订单货物价格信息
            /*PriceDetailDto priceDetailDTO = orderItem.getPriceDetailDto();

            //如果未修改过金额则记录订单原始价格
            if (priceDetailDTO.getOriginalPrice() == null) {
                priceDetailDTO.setOriginalPrice(orderItem.getFlowPrice());
            }
            //获取订单货物占订单金额的百分比
            double priceFluctuationRatio = CurrencyUtil.div(priceDetailDTO.getOriginalPrice(), order.getPriceDetailDto().getOriginalPrice());

            //计算修改后的订单货物金额
            double flowPrice = CurrencyUtil.mul(order.getFlowPrice(), priceFluctuationRatio);

            // 记录修改金额
            priceDetailDTO.setUpdatePrice(CurrencyUtil.sub(priceDetailDTO.getOriginalPrice(), flowPrice));
            priceDetailDTO.setFlowPrice(flowPrice);


            // 最终结算金额 = 流水金额-平台佣金-分销提佣
            double billPrice = priceDetailDTO.getFlowPrice();
            priceDetailDTO.setBillPrice(billPrice);

            //修改订单货物金额
            orderItem.setFlowPrice(flowPrice);
            orderItem.setPriceDetail(JSONUtil.toJsonStr(priceDetailDTO));
            orderItemService.update(orderItem, new LambdaUpdateWrapper<OrderItem>().eq(OrderItem::getId, orderItem.getId()));*/
        }
    }

}
