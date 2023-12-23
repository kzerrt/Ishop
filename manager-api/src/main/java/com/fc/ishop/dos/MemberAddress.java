package com.fc.ishop.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fc.ishop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * 会员地址类
 * @author florence
 * @date 2023/12/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("i_member_address")
public class MemberAddress extends BaseEntity {

    private static final long serialVersionUID = 13325524221L;

    //@ApiModelProperty(value = "会员ID", hidden = true)
    private String memberId;

    @NotEmpty(message = "收货人姓名不能为空")
    //@ApiModelProperty(value = "收货人姓名")
    private String name;

    //@Mobile
    //@ApiModelProperty(value = "手机号码")
    private String mobile;

    @NotBlank(message = "地址不能为空")
    //@ApiModelProperty(value = "地址名称， '，'分割")
    private String consigneeAddressPath;

    @NotBlank(message = "地址不能为空")
    //@ApiModelProperty(value = "地址id，'，'分割 ")
    private String consigneeAddressIdPath;

    @NotEmpty(message = "详细地址不能为空")
    //@ApiModelProperty(value = "详细地址")
    private String detail;

    //@ApiModelProperty(value = "是否为默认收货地址")
    private Boolean isDefault;

    //@ApiModelProperty(value = "地址别名")
    private String alias;

    //@ApiModelProperty(value = "经度")
    private String lon;

    //@ApiModelProperty(value = "纬度")
    private String lat;
}
