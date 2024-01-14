package com.fc.ishop.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fc.ishop.base.BaseEntity;
import com.fc.ishop.dto.AdminStoreApplyDto;
import com.fc.ishop.enums.StoreStatusEnum;
import com.fc.ishop.utils.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 店铺
 * @author florence
 * @date 2023/12/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("i_store")
@NoArgsConstructor
public class Store extends BaseEntity {
    private static final long serialVersionUID = -5861767726387892272L;

    /**
     * 会员Id
     */
    private String memberId;
    /**
     *会员名称
     */
    private String memberName;
    /**
     *店铺名称
     */
    private String storeName;
    /**
     *店铺关闭时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date storeEndTime;

    /**
     * 店铺状态
     * @see StoreStatusEnum
     */
    private String storeDisable;
    /**
     *是否自营
     */
    private Boolean selfOperated;
    /**
     *店铺logo
     */
    private String storeLogo;
    /**
     *经纬度
     */
    @NotEmpty
    private String storeCenter;
    /**
     *店铺简介
     */
    @Size(min = 6, max = 200, message = "店铺简介需在6-200字符之间")
    @NotBlank(message = "店铺简介不能为空")
    private String storeDesc;
    /**
     *地址名称， '，'分割
     */
    private String storeAddressPath;
    /**
     *地址id，'，'分割
     */
    private String storeAddressIdPath;
    /**
     *详细地址
     */
    private String storeAddressDetail;
    /**
     *描述评分
     */
    private Double descriptionScore;
    /**
     *服务评分
     */
    private Double serviceScore;
    /**
     *物流描述
     */
    private Double deliveryScore;
    /**
     *商品数量
     */
    private Integer goodsNum;
    /**
     *收藏数量
     */
    private Integer collectionNum;

    public Store(Member member) {
        this.memberId = member.getId();
        this.memberName = member.getUsername();
        storeDisable = StoreStatusEnum.APPLY.value();
        selfOperated = false;
        deliveryScore = 5.0;
        serviceScore = 5.0;
        descriptionScore = 5.0;
        goodsNum=0;
        collectionNum=0;
    }

    public Store(Member member, AdminStoreApplyDto adminStoreApplyDTO) {
        BeanUtil.copyProperties(adminStoreApplyDTO, this);

        this.memberId = member.getId();
        this.memberName = member.getUsername();
        storeDisable = StoreStatusEnum.APPLYING.value();
        selfOperated = false;
        deliveryScore = 5.0;
        serviceScore = 5.0;
        descriptionScore = 5.0;
        goodsNum=0;
        collectionNum=0;
    }
}
