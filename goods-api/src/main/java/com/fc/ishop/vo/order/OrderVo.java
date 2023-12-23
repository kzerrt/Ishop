package com.fc.ishop.vo.order;

import com.fc.ishop.dos.trade.Order;
import com.fc.ishop.dos.trade.OrderItem;
import lombok.Data;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/18
 */
@Data
public class OrderVo extends Order {
    private static final long serialVersionUID = 5820637554656388777L;

    //@ApiModelProperty(value = "订单商品项目")
    private List<OrderItem> orderItems;
}
