package com.fc.ishop.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author florence
 * @date 2023/12/18
 */
@Data
public class MemberAddressDto {
    @NotEmpty(message = "收货人姓名不能为空")
    //(value = "收货人姓名")
    private String consigneeName;

    //(value = "手机号码")
    private String consigneeMobile;

    @NotBlank(message = "地址不能为空")
    //(value = "地址名称， '，'分割")
    private String consigneeAddressPath;

    @NotBlank(message = "地址不能为空")
    //(value = "地址id，'，'分割 ")
    private String consigneeAddressIdPath;

    @NotEmpty(message = "详细地址不能为空")
    //(value = "详细地址")
    private String consigneeDetail;
}
