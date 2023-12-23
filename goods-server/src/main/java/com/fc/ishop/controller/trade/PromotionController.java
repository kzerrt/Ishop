package com.fc.ishop.controller.trade;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dto.PromotionGoodsDto;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.service.PromotionGoodsService;
import com.fc.ishop.service.PromotionService;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.web.manager.PromotionManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 促销商品接口类
 * @author florence
 * @date 2023/12/22
 */
@RestController
public class PromotionController implements PromotionManagerClient {
    @Autowired
    private PromotionService promotionService;
    // 促销商品
    @Autowired
    private PromotionGoodsService promotionGoodsService;

    @Override
    public ResultMessage<Map<String, Object>> getCurrentPromotion() {
        Map<String, Object> currentPromotion = promotionService.getCurrentPromotion();
        return ResultUtil.data(currentPromotion);
    }

    @Override
    public ResultMessage<Page<PromotionGoodsDto>> getPromotionGoods(String promotionId, String promotionType, PageVo pageVo) {
        Page<PromotionGoodsDto> promotionGoods = promotionGoodsService.getCurrentPromotionGoods(promotionType, pageVo);
        return ResultUtil.data(promotionGoods);
    }
}
