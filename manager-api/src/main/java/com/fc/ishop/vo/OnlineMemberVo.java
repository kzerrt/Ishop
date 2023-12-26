package com.fc.ishop.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * 在线会员
 * @author florence
 * @date 2023/12/26
 */
@Data
@AllArgsConstructor
public class OnlineMemberVo {
    //在线时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH")
    private Date date;

    //在线会员人数
    private Integer num;
}
