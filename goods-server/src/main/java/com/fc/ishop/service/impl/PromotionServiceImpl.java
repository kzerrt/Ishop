package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.ishop.dos.trade.SecKill;
import com.fc.ishop.enums.PromotionStatusEnum;
import com.fc.ishop.enums.PromotionTypeEnum;
import com.fc.ishop.service.PromotionService;
import com.fc.ishop.service.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author florence
 * @date 2023/12/22
 */
@Service("promotionService")
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private SecKillService secKillService;
    @Override
    public Map<String, Object> getCurrentPromotion() {
        Map<String, Object> res = new HashMap<>(16);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("promotion_status", PromotionStatusEnum.START.name());
        queryWrapper.gt("start_time", new Date());
        queryWrapper.lt("end_time", new Date());
        List<SecKill> list = secKillService.list(queryWrapper);
        if (list != null && !list.isEmpty()) {
            for (SecKill secKill : list) {
                res.put(PromotionTypeEnum.SECKILL.name(), secKill);
            }
        }
        return res;
    }
}
