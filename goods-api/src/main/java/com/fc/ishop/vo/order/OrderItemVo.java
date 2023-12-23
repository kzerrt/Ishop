package com.fc.ishop.vo.order;

import lombok.Data;

/**
 * 子订单
 * @author florence
 * @date 2023/12/18
 */
@Data
public class OrderItemVo {
    //(value = "编号")
    private String sn;

    //(value = "商品ID")
    private String goodsId;

    //(value = "货品ID")
    private String skuId;

    //(value = "销售量")
    private String num;

    //(value = "图片")
    private String image;

    //(value = "商品名称")
    private String name;

    //(value = "商品名称")
    private Double goodsPrice;

    /**
     * OrderItemAfterSaleStatusEnum
     */
    //(value = "售后状态", allowableValues = "NOT_APPLIED(未申请),ALREADY_APPLIED(已申请),EXPIRED(已失效不允许申请售后)")
    private String afterSaleStatus;

    /**
     *  OrderComplaintStatusEnum
     */
    //(value = "投诉状态")
    private String complainStatus;

    /**
     *  CommentStatusEnum
     */
    //(value = "评论状态:未评论(UNFINISHED),待追评(WAIT_CHASE),评论完成(FINISHED)，")
    private String commentStatus;


    public OrderItemVo(String sn, String goodsId, String skuId, String num, String image, String name, String afterSaleStatus, String complainStatus, String commentStatus, Double goodsPrice) {
        this.sn = sn;
        this.goodsId = goodsId;
        this.skuId = skuId;
        this.num = num;
        this.image = image;
        this.name = name;
        this.afterSaleStatus = afterSaleStatus;
        this.complainStatus = complainStatus;
        this.commentStatus = commentStatus;
        this.goodsPrice = goodsPrice;
    }

}
