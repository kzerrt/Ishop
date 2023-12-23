package com.fc.ishop.util;

import cn.hutool.core.text.CharSequenceUtil;
import com.fc.ishop.dos.trade.PromotionGoods;
import com.fc.ishop.dto.BasePromotion;
import com.fc.ishop.dto.SecKillSearchParams;
import com.fc.ishop.enums.PromotionTypeEnum;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.utils.DateUtil;
import com.fc.ishop.vo.SecKillVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * 抢购活动工具类
 * @author florence
 * @date 2023/12/20
 */
public class PromotionTools {
    /**
     * 参数验证
     * 1、活动起始时间必须大于当前时间
     * 2、验证活动开始时间是否大于活动结束时间
     *
     * @param startTime 活动开始时间
     * @param endTime   活动结束时间
     */
    public static void checkPromotionTime(Long startTime, Long endTime) {

        long nowTime = DateUtil.getDateline() * 1000;

        //如果活动起始时间小于现在时间
        if (startTime < nowTime) {
            throw new ServiceException("活动起始时间必须大于当前时间");
        }

        // 开始时间不能大于结束时间
        if (startTime > endTime) {
            throw new ServiceException("活动起始时间不能大于活动结束时间");
        }
    }

    public static List<PromotionGoods> promotionGoodsInit(List<PromotionGoods> originList, BasePromotion promotion, PromotionTypeEnum promotionTypeEnum) {
        // 本次促销商品入库
        for (PromotionGoods promotionGoods : originList) {
            promotionGoods.setPromotionId(promotion.getId());
            promotionGoods.setStoreName(promotion.getStoreName());
            promotionGoods.setTitle(promotion.getPromotionName());
            promotionGoods.setStartTime(promotion.getStartTime());
            if (promotion.getEndTime() == null) {
                promotionGoods.setEndTime(promotion.getEndTime());
            }
            promotionGoods.setPromotionType(promotionTypeEnum.name());
            promotionGoods.setPromotionStatus(promotion.getPromotionStatus());
            promotionGoods.setNum(0);
        }
        return originList;
    }
}
