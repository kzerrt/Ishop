package com.fc.ishop.vo.order;

import com.fc.ishop.dos.trade.Order;
import com.fc.ishop.dos.trade.OrderItem;
import com.fc.ishop.enums.DeliverStatusEnum;
import com.fc.ishop.enums.DeliveryMethodEnum;
import com.fc.ishop.enums.OrderStatusEnum;
import com.fc.ishop.enums.PayStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/18
 */
@Data
public class OrderDetailVo implements Serializable {


    private static final long serialVersionUID = -6293102172184734928L;

    /**
     * 订单
     */
    private Order order;

    /**
     * 子订单信息
     */
    private List<OrderItem> orderItems;

    /**
     * 订单状态
     */
    private String orderStatusValue;

    /**
     * 付款状态
     */
    private String payStatusValue;

    /**
     * 物流状态
     */
    private String deliverStatusValue;

    /**
     * 物流类型
     */
    private String deliveryMethodValue;

    /**
     * 支付类型
     */
    private String paymentMethodValue;


    //(value = "价格详情")
    private String priceDetail;

    public OrderDetailVo(Order order, List<OrderItem> orderItems) {
        this.order = order;
        this.orderItems = orderItems;
    }

    /**
     * 可操作类型
     */
    public AllowOperation getAllowOperationVO() {
        return new AllowOperation(this.order);
    }

    public String getOrderStatusValue() {
        try {
            return OrderStatusEnum.valueOf(order.getOrderStatus()).description();
        } catch (Exception e) {
            return "";
        }
    }

    public String getPayStatusValue() {
        try {
            return PayStatusEnum.valueOf(order.getPayStatus()).description();
        } catch (Exception e) {
            return "";
        }

    }

    public String getDeliverStatusValue() {
        try {
            return DeliverStatusEnum.valueOf(order.getDeliverStatus()).getDescription();
        } catch (Exception e) {
            return "";
        }
    }

    public String getDeliveryMethodValue() {
        try {
            return DeliveryMethodEnum.valueOf(order.getDeliveryMethod()).getDescription();
        } catch (Exception e) {
            return "";
        }
    }

    public OrderDetailVo() {
    }
}
