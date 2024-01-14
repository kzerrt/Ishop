package com.fc.ishop.dos;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 商家订单流水
 * @author florence
 * @date 2023/12/26
 */

@Data
@Entity
@Table(name = "i_store_flow")
@TableName("i_store_flow")
public class StoreFlow {
    @Id
    @TableId
    @TableField
    @Column(columnDefinition = "bigint(20)")
    //(value = "唯一标识", hidden = true)
    private String id;

    //(value = "流水编号")
    private String sn;

    //(value = "订单sn")
    private String orderSn;

    //(value = "子订单sn")
    private String orderItemSn;

    //(value = "商家id")
    private String storeId;

    //(value = "商家名称 ")
    private String storeName;

    //(value = "会员id")
    private String memberId;

    //(value = "会员名称")
    private String memberName;


    //(value = "商品ID")
    private String goodsId;

    //(value = "商品名称")
    private String goodsName;

    //(value = "货品ID")
    private String skuId;

    //(value = "分类ID")
    private String categoryId;

    //(value = "规格json")
    @Column(columnDefinition = "TEXT")
    private String specs;


    //(value = "流水类型：PAY/REFUND 支付/退款", allowableValues = "PAY,REFUND")
    private String flowType;


    //(value = "流水金额")
    private Double finalPrice;

    //(value = "最终结算金额")
    private Double billPrice;

    //(value = "销售量")
    private Integer num;

    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
