package com.fc.ishop.service;


import java.util.Map;

/**
 * 促销商品类
 * @author florence
 * @date 2023/12/22
 */
public interface PromotionService {
    /**
     * 获取当前活动类
     * @return
     */
    Map<String, Object> getCurrentPromotion();

    boolean updatePromotionStatus(Object promotionMessage);
}
