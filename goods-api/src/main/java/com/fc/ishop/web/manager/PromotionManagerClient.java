package com.fc.ishop.web.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dto.PromotionGoodsDto;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.ResultMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author florence
 * @date 2023/12/22
 */
@FeignClient(value = "goods-server", contextId = "promotionManagerClient")
public interface PromotionManagerClient {

    /**
     * 获取当前抢购活动
     * @return
     */
    @GetMapping("/manager-p/current")
    ResultMessage<Map<String, Object>> getCurrentPromotion();

    /**
     * 获取当前抢购活动的商品
     */
    @PostMapping("/manager-p/goods/{promotionId}")
    ResultMessage<Page<PromotionGoodsDto>> getPromotionGoods(@PathVariable String promotionId,
                                                             @RequestParam("promotionType") String promotionType, @RequestBody PageVo pageVo);
}
