package com.fc.ishop.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fc.ishop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * 会员类
 * @author florence
 * @date 2023/12/1
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@TableName("i_member")
public class Member extends BaseEntity {
    private static final long serialVersionUID = 1L;

    //@ApiModelProperty(value = "会员用户名")
    private String username;

    //@ApiModelProperty(value = "会员密码")
    private String password;

    //@ApiModelProperty(value = "昵称")
    private String nickName;

    @Min(message = "会员性别参数错误", value = 0)
    //@ApiModelProperty(value = "会员性别,1为男，0为女")
    private Integer sex;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //@ApiModelProperty(value = "会员生日")
    private Date birthday;

    //@ApiModelProperty(value = "会员地址ID")
    private String regionId;

    //@ApiModelProperty(value = "会员地址")
    private String region;

    @NotEmpty(message = "手机号码不能为空")
    //@ApiModelProperty(value = "手机号码", required = true)
    private String mobile;

    @Min(message = "必须为数字", value = 0)
    //@ApiModelProperty(value = "积分数量")
    private Long point;

    //@ApiModelProperty(value = "会员头像")
    private String face;

    //@ApiModelProperty(value = "会员状态")
    private Boolean disabled;

    //@ApiModelProperty(value = "是否开通店铺")
    private Boolean haveStore;

    //@ApiModelProperty(value = "店铺ID")
    private String storeId;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    //@ApiModelProperty(value = "最后一次登录时间")
    private Date lastLoginDate;

    public Member(String username, String password, String mobile) {
        this.username = username;
        this.password = password;
        this.mobile = mobile;
        this.nickName = mobile;
        this.disabled = true;
        this.haveStore = false;
        this.sex = 0;
        this.point = 0L;
        this.lastLoginDate = new Date();
    }

    public Member(String username, String password, String mobile, String nickName, String face) {
        this.username = username;
        this.password = password;
        this.mobile = mobile;
        this.nickName = nickName;
        this.disabled = true;
        this.haveStore = false;
        this.face = face;
        this.sex = 0;
        this.point = 0L;
        this.lastLoginDate = new Date();
    }

    public Member(String username, String password, String face, String nickName, Integer sex) {
        this.username = username;
        this.password = password;
        this.mobile = "";
        this.nickName = nickName;
        this.disabled = true;
        this.haveStore = false;
        this.face = face;
        this.sex = sex;
        this.point = 0L;
        this.lastLoginDate = new Date();
    }
}
