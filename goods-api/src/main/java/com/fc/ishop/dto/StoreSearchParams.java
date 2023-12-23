package com.fc.ishop.dto;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.ishop.enums.StoreStatusEnum;
import com.fc.ishop.utils.StringUtils;

import java.io.Serializable;

/**
 * @author florence
 * @date 2023/12/18
 */
public class StoreSearchParams implements Serializable {
    private static final long serialVersionUID = 6916054310764833369L;

    //@ApiModelProperty(value = "会员名称")
    private String memberName;

    //@ApiModelProperty(value = "店铺名称")
    private String storeName;
    /**
     * @see StoreStatusEnum
     */
    //@ApiModelProperty(value = "店铺状态")
    private String storeDisable;

    //@ApiModelProperty(value = "开始时间")
    private String startDate;

    //@ApiModelProperty(value = "结束时间")
    private String endDate;

    public <T> QueryWrapper<T> queryWrapper() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(storeName)) {
            queryWrapper.like("store_name", storeName);
        }
        if (StringUtils.isNotEmpty(memberName)) {
            queryWrapper.like("member_name", memberName);
        }
        if (StringUtils.isNotEmpty(storeDisable)) {
            queryWrapper.eq("store_disable", storeDisable);
        } else {
            queryWrapper.eq("store_disable", StoreStatusEnum.OPEN.name()).or().eq("store_disable", StoreStatusEnum.CLOSED.name());
        }
        // 按时间查询
        if (StringUtils.isNotEmpty(startDate)) {
            queryWrapper.ge("create_time", DateUtil.parse(startDate));
        }
        if (StringUtils.isNotEmpty(endDate)) {
            queryWrapper.le("create_time", DateUtil.parse(endDate));
        }
        return queryWrapper;
    }
}
