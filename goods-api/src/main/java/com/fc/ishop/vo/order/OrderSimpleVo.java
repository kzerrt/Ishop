package com.fc.ishop.vo.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fc.ishop.utils.StringUtils;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/18
 */
@Data
public class OrderSimpleVo {
    //@ApiModelProperty("sn")
    private String sn;

    //@ApiModelProperty(value = "总价格")
    private Double flowPrice;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * OrderStatusEnum
     */
    //@ApiModelProperty(value = "订单状态")
    private String orderStatus;

    /**
     *  PayStatusEnum
     */
    //@ApiModelProperty(value = "付款状态")
    private String payStatus;

    //@ApiModelProperty(value = "支付方式")
    private String paymentMethod;

    //@ApiModelProperty(value = "支付时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date paymentTime;

    //@ApiModelProperty(value = "用户名")
    private String memberName;

    //@ApiModelProperty(value = "店铺名称")
    private String storeName;

    //@ApiModelProperty(value = "店铺ID")
    private String storeId;


    /**
     * 子订单信息
     */
    private List<OrderItemVo> orderItems;

    //@ApiModelProperty(hidden = true, value = "item goods_id")
    private String groupGoodsId;

    //@ApiModelProperty(hidden = true, value = "item sku id")
    private String groupSkuId;

    //@ApiModelProperty(hidden = true, value = "item 数量")
    private String groupNum;

    //@ApiModelProperty(hidden = true, value = "item 图片")
    private String groupImages;

    //@ApiModelProperty(hidden = true, value = "item 名字")
    private String groupName;
    //@ApiModelProperty(hidden = true, value = "item 编号")
    private String groupOrderItemsSn;

    //@ApiModelProperty(hidden = true, value = "item 商品价格")
    private String groupGoodsPrice;
    /**
     * OrderItemAfterSaleStatusEnum
     */
    //@ApiModelProperty(hidden = true, value = "item 售后状态", allowableValues = "NOT_APPLIED(未申请),ALREADY_APPLIED(已申请),EXPIRED(已失效不允许申请售后)")
    
    private String groupAfterSaleStatus;

    /**
     *  OrderComplaintStatusEnum
     */
    //@ApiModelProperty(hidden = true, value = "item 投诉状态")
    
    private String groupComplainStatus;

    /**
     *  CommentStatusEnum
     */
    //@ApiModelProperty(hidden = true, value = "item 评价状态")
    
    private String groupCommentStatus;


    /**
     *  OrderTypeEnum
     */
    //@ApiModelProperty(value = "订单类型")
    private String orderType;

    /**
     *  DeliverStatusEnum
     */
    //@ApiModelProperty(value = "货运状态")
    private String deliverStatus;

    @Getter
    public List<OrderItemVo> getOrderItems() {
        if (StringUtils.isEmpty(groupGoodsId)) {
            return new ArrayList<>();
        }
        List<OrderItemVo> orderItemVoS = new ArrayList<>();
        String[] orderItemsSn = groupOrderItemsSn.split(",");
        String[] goodsId = groupGoodsId.split(",");
        String[] skuId = groupSkuId.split(",");
        String[] num = groupNum.split(",");
        String[] image = groupImages.split(",");
        String[] name = groupName.split(",");
        String[] afterSaleStatus = groupAfterSaleStatus.split(",");
        String[] complainStatus = groupComplainStatus.split(",");
        String[] commentStatus = groupCommentStatus.split(",");
        String[] goodsPrice = groupGoodsPrice.split(",");
//        String goodsId, String skuId, Integer num, String image, String name, String afterSaleStatus
        for (int i = 0; i < goodsId.length; i++) {
            orderItemVoS.add(new OrderItemVo(orderItemsSn[i], goodsId[i], skuId[i], num[i], image[i], name[i], afterSaleStatus[i], complainStatus[i], commentStatus[i], Double.parseDouble(goodsPrice[i])));
        }
        return orderItemVoS;

    }

    /**
     * 初始化自身状态
     */
    @Getter
    public AllowOperation getAllowOperationVo() {
        //设置订单的可操作状态
        return new AllowOperation(this);
    }

}
