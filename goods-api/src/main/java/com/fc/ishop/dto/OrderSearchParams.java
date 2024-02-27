package com.fc.ishop.dto;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fc.ishop.enums.OrderStatusEnum;
import com.fc.ishop.enums.OrderTagEnum;
import com.fc.ishop.enums.UserEnums;
import com.fc.ishop.security.context.UserContext;
import com.fc.ishop.utils.DateUtil;
import com.fc.ishop.utils.StringUtils;
import com.fc.ishop.vo.PageVo;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author florence
 * @date 2023/12/18
 */
@Data
public class OrderSearchParams extends PageVo {
    private static final long serialVersionUID = -6380573339089959194L;

    //@ApiModelProperty(value = "商品名称")
    private String goodsName;

    //@ApiModelProperty(value = "订单编号")
    private String orderSn;

    /*@ApiModelProperty(value = "页面标签",
            example = "ALL:全部," +
                    "WAIT_PAY:待付款," +
                    "WAIT_ROG:待收货," +
                    "CANCELLED:已取消," +
                    "COMPLETE:已完成")*/
    private String tag;

    //@ApiModelProperty(value = "商家ID")
    private String storeId;

    //@ApiModelProperty(value = "会员ID")
    private String memberId;

    //@ApiModelProperty(value = "收货人")
    private String shipName;

    //@ApiModelProperty(value = "买家昵称")
    private String buyerName;

   // @ApiModelProperty(value = "订单状态")
    private String orderStatus;

    //@ApiModelProperty(value = "付款状态")
    private String payStatus;

    //@ApiModelProperty(value = "关键字 商品名称/买家名称/店铺名称")
    private String keywords;

    //@ApiModelProperty(value = "付款方式")
    private String paymentType;

    /**
     * OrderTypeEnum
     */
    //@ApiModelProperty(value = "订单类型")
    private String orderType;

    //@ApiModelProperty(value = "支付方式")
    private String paymentMethod;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@ApiModelProperty(value = "支付时间")
    private Date paymentTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //@ApiModelProperty(value = "下单开始时间")
    private Date startDate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //@ApiModelProperty(value = "下单结束时间")
    private Date endDate;

    //@ApiModelProperty(value = "订单来源")
    private String clientType;

    public <T> QueryWrapper<T> queryWrapper() {
        QueryWrapper<T> wrapper = new QueryWrapper<>();

        //关键字查询
        if (StringUtils.isNotEmpty(keywords)) {
            wrapper.like("o.sn", keywords);
            wrapper.like("oi.goods_name", keywords);
        }
        // 按卖家查询
        if (StringUtils.equals(UserContext.getCurrentUser().getRole().name(), UserEnums.STORE.name())) {
            wrapper.eq("o.store_id", UserContext.getCurrentUser().getStoreId());
        }
        if (StringUtils.equals(UserContext.getCurrentUser().getRole().name(), UserEnums.MANAGER.name())
                && StringUtils.isNotEmpty(storeId)) {
            wrapper.eq("o.store_id", storeId);
        }
        // 按买家查询
        if (StringUtils.equals(UserContext.getCurrentUser().getRole().name(), UserEnums.MEMBER.name())) {
            wrapper.eq("o.member_id", UserContext.getCurrentUser().getId());
        }
        // 按照买家查询
        if (StringUtils.isNotEmpty(memberId)) {
            wrapper.like("o.member_id", memberId);
        }

        // 按订单编号查询
        if (StringUtils.isNotEmpty(orderSn)) {
            wrapper.like("o.sn", orderSn);
        }

        // 按时间查询
        if (startDate != null) {
            wrapper.ge("o.create_time", startDate);
        }
        if (endDate != null) {
            wrapper.le("o.create_time", DateUtil.endOfDate(endDate));
        }
        // 按购买人用户名
        if (StringUtils.isNotEmpty(buyerName)) {
            wrapper.like("o.member_name", buyerName);
        }

        // 按订单类型
        if (StringUtils.isNotEmpty(orderType)) {
            wrapper.eq("o.order_type", orderType);
        }

        // 按支付方式
        if (StringUtils.isNotEmpty(paymentMethod)) {
            wrapper.eq("o.payment_method", paymentMethod);
        }

        // 按标签查询
        if (StringUtils.isNotEmpty(tag)) {
            String orderStatusColumn = "o.order_status";
            OrderTagEnum tagEnum = OrderTagEnum.valueOf(tag);
            switch (tagEnum) {
                //待付款
                case WAIT_PAY:
                    wrapper.eq(orderStatusColumn, OrderStatusEnum.UNPAID.name());
                    break;
                //待发货
                case WAIT_SHIP:
                    wrapper.eq(orderStatusColumn, OrderStatusEnum.UNDELIVERED.name());
                    break;
                //待收货
                case WAIT_ROG:
                    wrapper.eq(orderStatusColumn, OrderStatusEnum.DELIVERED.name());
                    break;
                //已取消
                case CANCELLED:
                    wrapper.eq(orderStatusColumn, OrderStatusEnum.CANCELLED.name());
                    break;
                //已完成
                case COMPLETE:
                    wrapper.eq(orderStatusColumn, OrderStatusEnum.COMPLETED.name());
                    break;
                default:
                    break;
            }
        }

        if (StringUtils.isNotEmpty(shipName)) {
            wrapper.like("o.ship_name", shipName);
        }
        // 按商品名称查询
        if (StringUtils.isNotEmpty(goodsName)) {
            wrapper.like("oi.goods_name", goodsName);
        }

        //付款方式
        if (StringUtils.isNotEmpty(paymentType)) {
            wrapper.like("o.payment_type", paymentType);
        }

        //订单状态
        if (StringUtils.isNotEmpty(orderStatus)) {
            wrapper.eq("o.order_status", orderStatus);
        }

        //付款状态
        if (StringUtils.isNotEmpty(payStatus)) {
            wrapper.eq("o.pay_status", payStatus);
        }

        //订单来源
        if (StringUtils.isNotEmpty(clientType)) {
            wrapper.like("o.client_type", clientType);
        }
        wrapper.eq("o.delete_flag", false);
        wrapper.groupBy("o.id");
        wrapper.orderByDesc("o.id");

        return wrapper;
    }
}
