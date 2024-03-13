package com.fc.ishop.dos.statistics;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 会员统计
 * @author florence
 * @date 2023/12/24
 */
@Data
@TableName("i_member_statistics_data")
public class MemberStatisticsData {
    @TableId
    @TableField
    private String id;

    //(value = "统计日")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createDate;

    //(value = "当前会员数量")
    private Integer memberCount;

    //(value = "新增会员数量")
    private Integer newlyAdded;

    //(value = "当日活跃数量")
    private Integer activeQuantity;
}
