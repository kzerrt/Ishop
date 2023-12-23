package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.trade.PromotionGoods;
import com.fc.ishop.dto.PromotionGoodsDto;
import com.fc.ishop.enums.PromotionTypeEnum;
import com.fc.ishop.vo.PageVo;

/**
 * @author florence
 * @date 2023/12/21
 */
public interface PromotionGoodsService extends IService<PromotionGoods> {
    // 缓存商品key
    static String getPromotionGoodsStockCacheKey(PromotionTypeEnum typeEnum, String promotionId, String skuId) {
        return "{seckill}_" + promotionId + "_" + skuId;
    }

    /**
     * 获取促销活动商品库存
     *
     * @param typeEnum    促销商品类型
     * @param promotionId 促销活动id
     * @param skuId       商品skuId
     * @return 促销活动商品库存
     */
    Integer getPromotionGoodsStock(PromotionTypeEnum typeEnum, String promotionId, String skuId);

    /**
     * 根据类型获取当前活动商品
     * @param promotionType
     * @param pageVo
     * @return
     */
    Page<PromotionGoodsDto> getCurrentPromotionGoods(String promotionType, PageVo pageVo);
}
