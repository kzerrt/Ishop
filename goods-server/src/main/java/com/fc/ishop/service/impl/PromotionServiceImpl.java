package com.fc.ishop.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fc.ishop.delayqueue.PromotionMessage;
import com.fc.ishop.dos.trade.SecKill;
import com.fc.ishop.enums.PromotionApplyStatusEnum;
import com.fc.ishop.enums.PromotionStatusEnum;
import com.fc.ishop.enums.PromotionTypeEnum;
import com.fc.ishop.service.PromotionService;
import com.fc.ishop.service.SecKillService;
import com.fc.ishop.vo.SecKillVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author florence
 * @date 2023/12/22
 */
@Service("promotionService")
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private SecKillService secKillService;
    @Autowired
    private MongoTemplate mongoTemplate;
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

    @Override
    public boolean updatePromotionStatus(Object msg) {
        PromotionMessage promotionMessage = (PromotionMessage) msg;
        SecKillVo seckill = this.mongoTemplate.findById(promotionMessage.getPromotionId(), SecKillVo.class);
        if (seckill == null) {
            return false;
        }
        seckill.setPromotionStatus(promotionMessage.getPromotionStatus());
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", promotionMessage.getPromotionId());
        updateWrapper.set("promotion_status", PromotionStatusEnum.valueOf(promotionMessage.getPromotionStatus()));
        boolean result = this.secKillService.update(updateWrapper);
        this.mongoTemplate.save(seckill);
        return result;
    }
}
