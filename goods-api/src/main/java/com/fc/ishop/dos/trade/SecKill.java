package com.fc.ishop.dos.trade;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fc.ishop.dto.BasePromotion;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 限时抢购活动
 * @author florence
 * @date 2023/12/20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("i_seckill")
public class SecKill extends BasePromotion {
    private static final long serialVersionUID = -9116425737163730836L;

    @NotNull(message = "请填写报名截止时间")
    //(value = "报名截至时间", required = true)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyEndTime;

    //(value = "申请规则")
    private String seckillRule;

    //(value = "开启几点场 例如：6，8，12")
    @NotNull(message = "活动时间段不能为空")
    private String hours;

    /**
     * 已参与此活动的商家id集合
     */
    //(value = "商家id集合以逗号分隔")
    private String storeIds;
}
