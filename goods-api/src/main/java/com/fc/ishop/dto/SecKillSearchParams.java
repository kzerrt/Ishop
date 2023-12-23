package com.fc.ishop.dto;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.ishop.enums.PromotionApplyStatusEnum;
import com.fc.ishop.enums.PromotionStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author florence
 * @date 2023/12/20
 */
@Data
public class SecKillSearchParams implements Serializable {

    private static final long serialVersionUID = -4052716630253333681L;

   //(value = "限时抢购活动编号")
    private String seckillId;

   //(value = "活动名称")
    private String promotionName;

   //(value = "时刻")
    private Integer timeLine;

   //(value = "商家id")
    private String[] storeIds;

   //(value = "商家编号")
    private String storeId;

   //(value = "商品名称")
    private String goodsName;

   //(value = "活动开始时间", required = true)
    private Long startTime;

   //(value = "活动结束时间", required = true)
    private Long endTime;

    /**
     *  PromotionStatusEnum
     */
   //(value = "活动状态")
    private String promotionStatus;

    /**
     * PromotionApplyStatusEnum
     */
   //(value = "APPLY(\"申请\"), PASS(\"通过\"), REFUSE(\"拒绝\")")
    private String promotionApplyStatus;

    public <T> QueryWrapper<T> wrapper() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if (CharSequenceUtil.isNotEmpty(goodsName)) {
            queryWrapper.like("goods_name", goodsName);
        }
        if (CharSequenceUtil.isNotEmpty(promotionName)) {
            queryWrapper.like("promotion_name", promotionName);
        }
        if (CharSequenceUtil.isNotEmpty(seckillId)) {
            queryWrapper.eq("seckill_id", seckillId);
        }
        if (storeIds != null) {
            queryWrapper.in("store_id", Arrays.asList(storeIds));
        }
        if (timeLine != null) {
            queryWrapper.eq("time_line", timeLine);
        }
        if (startTime != null) {
            queryWrapper.ge("start_time", new Date(startTime));
        }
        if (endTime != null) {
            queryWrapper.le("end_time", new Date(endTime));
        }
        if (CharSequenceUtil.isNotEmpty(promotionApplyStatus)) {
            queryWrapper.eq("promotion_apply_status", PromotionApplyStatusEnum.valueOf(promotionApplyStatus).name());
        }
        if (CharSequenceUtil.isNotEmpty(promotionStatus)) {
            queryWrapper.eq("promotion_status", PromotionStatusEnum.valueOf(promotionStatus).name());
        }
        return queryWrapper;
    }
}
