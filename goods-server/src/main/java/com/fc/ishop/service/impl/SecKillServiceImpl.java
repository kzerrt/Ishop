package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.delayqueue.DelayQueueTools;
import com.fc.ishop.delayqueue.DelayQueueType;
import com.fc.ishop.delayqueue.PromotionMessage;
import com.fc.ishop.dos.trade.PromotionGoods;
import com.fc.ishop.dos.trade.SecKill;
import com.fc.ishop.dos.trade.SecKillApply;
import com.fc.ishop.dto.SecKillSearchParams;
import com.fc.ishop.enums.PromotionStatusEnum;
import com.fc.ishop.enums.PromotionTypeEnum;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.mapper.SecKillMapper;
import com.fc.ishop.prop.RocketMQCustomProperties;
import com.fc.ishop.service.PromotionGoodsService;
import com.fc.ishop.service.SecKillApplyService;
import com.fc.ishop.service.SecKillService;
import com.fc.ishop.trigger.TimeExecuteConstant;
import com.fc.ishop.trigger.TimeTrigger;
import com.fc.ishop.trigger.TimeTriggerMsg;
import com.fc.ishop.util.MongoUtil;
import com.fc.ishop.util.PromotionTools;
import com.fc.ishop.utils.BeanUtil;
import com.fc.ishop.utils.DateUtil;
import com.fc.ishop.utils.StringUtils;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.SecKillVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author florence
 * @date 2023/12/20
 */
@Service("secKillService")
public class SecKillServiceImpl
        extends ServiceImpl<SecKillMapper, SecKill> implements SecKillService {
    @Autowired
    private SecKillApplyService secKillApplyService;
    @Autowired
    private PromotionGoodsService promotionGoodsService;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RocketMQCustomProperties rocketMQCustomProperties;
    @Autowired
    private TimeTrigger timeTrigger;
    @Override
    public boolean saveSeckill(SecKillVo seckill) {
        // 检查限时抢购参数
        checkSeckillParam(seckill, seckill.getStoreId());
        seckill.setPromotionStatus(PromotionStatusEnum.NEW.name());
        // 保存到MYSQL中
        boolean save = this.save(seckill);
        // 保存到mongo中
        if (save) {
            mongoTemplate.save(seckill);
        }
        this.addSeckillStartTask(seckill);
        return save;
    }

    @Override
    public void modifySeckill(SecKillVo seckillVo) {
        // 检查该限时抢购是否存在
        SecKillVo seckill = checkSeckillExist(seckillVo.getId());
        if (PromotionStatusEnum.START.name().equals(seckillVo.getPromotionStatus())) {
            throw new ServiceException("活动已经开始，不能进行编辑删除操作");
        }
        // 保存到mysql
        int result = baseMapper.updateById(seckillVo);
        mongoTemplate.save(seckillVo);
        if (seckill.getStartTime().getTime() != seckillVo.getStartTime().getTime()) {
            PromotionMessage promotionMessage = new PromotionMessage(seckillVo.getId(), PromotionTypeEnum.SECKILL.name(),
                    PromotionStatusEnum.START.name(), seckillVo.getStartTime(), seckillVo.getEndTime());
            // 更新延时任务
            this.timeTrigger.edit(TimeExecuteConstant.PROMOTION_EXECUTOR,// 执行器id
                    promotionMessage,// 参数
                    seckill.getStartTime().getTime(),// 旧时间
                    seckillVo.getStartTime().getTime(),// 新时间
                    DelayQueueTools.wrapperUniqueKey(DelayQueueType.PROMOTION, (promotionMessage.getPromotionType() + promotionMessage.getPromotionId())), // 唯一id
                    DateUtil.getDelayTime(seckillVo.getStartTime().getTime()),// 延迟时间
                    rocketMQCustomProperties.getPromotionTopic()); // 标题
        }
    }

    @Override
    public Page<SecKillVo> getSeckillByPageFromMongo(SecKillSearchParams queryParam, PageVo pageVo) {
        Page<SecKillVo> page = new Page<>(pageVo.getPageNumber(), pageVo.getPageSize());
        if (queryParam == null) {
            queryParam = new SecKillSearchParams();
        }
        Query query = MongoUtil.creatMongoQuery(queryParam);
        MongoUtil.mongoQueryPageParam(query, pageVo);
        page.setCurrent(pageVo.getPageNumber());
        page.setSize(pageVo.getPageSize());
        List<SecKillVo> secKillVos = mongoTemplate.find(query, SecKillVo.class);
        page.setRecords(secKillVos);
        page.setTotal(mongoTemplate.count(query, SecKillVo.class));
        return page;
    }

    @Override
    public void deleteSeckill(String id) {
        SecKill secKill = checkSeckillExist(id);
        if (PromotionStatusEnum.CLOSE.name().equals(secKill.getPromotionStatus()) || PromotionStatusEnum.END.name().equals(secKill.getPromotionStatus())) {
            //更新状态标志为删除
            LambdaUpdateWrapper<SecKill> secKillUpdateWrapper = new LambdaUpdateWrapper<>();
            secKillUpdateWrapper.eq(SecKill::getId, id)
                    .set(SecKill::getDeleteFlag, true) .set(SecKill::getPromotionStatus, PromotionStatusEnum.CLOSE.name());
            this.update(secKillUpdateWrapper);
            // 申请类
            LambdaUpdateWrapper<SecKillApply> seckillApplyLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            seckillApplyLambdaUpdateWrapper.eq(SecKillApply::getSeckillId, id).set(SecKillApply::getDeleteFlag, true);
            this.secKillApplyService.update(seckillApplyLambdaUpdateWrapper);
            // mongo 商品vo删除
            mongoTemplate.remove(new Query().addCriteria(Criteria.where("id").is(id)), SecKillVo.class);
            LambdaUpdateWrapper<PromotionGoods> promotionGoodsQueryWrapper =
                    new LambdaUpdateWrapper<PromotionGoods>().eq(PromotionGoods::getPromotionId, id).set(PromotionGoods::getDeleteFlag, true);
            this.promotionGoodsService.update(promotionGoodsQueryWrapper);
            this.timeTrigger.delete(TimeExecuteConstant.PROMOTION_EXECUTOR,
                    secKill.getStartTime().getTime(),
                    DelayQueueTools.wrapperUniqueKey(DelayQueueType.PROMOTION, (PromotionTypeEnum.SECKILL.name() + secKill.getId())),
                    rocketMQCustomProperties.getPromotionTopic());
        } else {
            throw new ServiceException("该限时抢购活动的状态不能删除");
        }
    }

    @Override
    public void closeSeckill(String id) {
        SecKillVo seckillVO = checkSeckillExist(id);
        if (PromotionStatusEnum.NEW.name().equals(seckillVO.getPromotionStatus()) || PromotionStatusEnum.START.name().equals(seckillVO.getPromotionStatus())) {
            LambdaUpdateWrapper<SecKill> updateWrapper =
                    new LambdaUpdateWrapper<>();
            updateWrapper.eq(SecKill::getId, id).set(SecKill::getPromotionStatus, PromotionStatusEnum.CLOSE.name());
            this.update(updateWrapper);
            seckillVO.setPromotionStatus(PromotionStatusEnum.CLOSE.name());
            this.mongoTemplate.save(seckillVO);
            if (PromotionStatusEnum.CLOSE.name().equals(seckillVO.getPromotionStatus())) {
                LambdaQueryWrapper<PromotionGoods> deleteWrapper = new LambdaQueryWrapper<>();
                deleteWrapper.eq(PromotionGoods::getPromotionId, seckillVO.getId());
                promotionGoodsService.remove(deleteWrapper);
                this.timeTrigger.delete(TimeExecuteConstant.PROMOTION_EXECUTOR,
                        seckillVO.getStartTime().getTime(),
                        DelayQueueTools.wrapperUniqueKey(DelayQueueType.PROMOTION, (PromotionTypeEnum.SECKILL.name() + seckillVO.getId())),
                        rocketMQCustomProperties.getPromotionTopic());
            }
        } else {
            throw new ServiceException("该限时抢购活动的状态不能关闭");
        }
    }

    @Override
    public void openSeckill(String id) {
        SecKillVo seckillVO = checkSeckillExist(id);
        // 校验时间准确性
        PromotionTools.checkPromotionTime(seckillVO.getStartTime().getTime(), seckillVO.getEndTime().getTime());
        if (PromotionStatusEnum.NEW.name().equals(seckillVO.getPromotionStatus()) || PromotionStatusEnum.CLOSE.name().equals(seckillVO.getPromotionStatus())) {
            LambdaUpdateWrapper<SecKill> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(SecKill::getId, id).set(SecKill::getPromotionStatus, PromotionStatusEnum.START.name());
            this.update(updateWrapper);
            seckillVO.setPromotionStatus(PromotionStatusEnum.START.name());
            this.mongoTemplate.save(seckillVO);
            this.addSeckillStartTask(seckillVO);
        }
    }

    @Override
    public void storeApply(String storeId, String seckillId) {
        SecKill seckill = this.getById(seckillId);
        String storeIds;
        if (!StringUtils.isEmpty(seckill.getStoreIds())) {
            storeIds = seckill.getStoreIds() + storeId + ",";
        } else {
            storeIds = storeId + ",";
        }
        seckill.setStoreIds(storeIds);
        this.updateById(seckill);
    }

    /**
     * 发送延时任务
     */
    private void addSeckillStartTask(SecKillVo seckill) {
        // 促销活动信息
        PromotionMessage promotionMessage = new PromotionMessage(seckill.getId(), PromotionTypeEnum.SECKILL.name(),
                PromotionStatusEnum.START.name(), seckill.getStartTime(), seckill.getEndTime());
        TimeTriggerMsg timeTriggerMsg = new TimeTriggerMsg(TimeExecuteConstant.PROMOTION_EXECUTOR,
                seckill.getStartTime().getTime(),
                promotionMessage,
                DelayQueueTools.wrapperUniqueKey(DelayQueueType.PROMOTION, (promotionMessage.getPromotionType() + promotionMessage.getPromotionId())),
                rocketMQCustomProperties.getPromotionTopic());
        // 发送促销活动延时任务
        timeTrigger.addDelay(timeTriggerMsg, DateUtil.getDelayTime(seckill.getStartTime().getTime()));
    }


    /**
     * 校验活动是否存在
     * @param id
     * @return
     */
    private SecKillVo checkSeckillExist(String id) {
        SecKillVo seckill = mongoTemplate.findById(id, SecKillVo.class);
        if (seckill == null) {
            throw new ServiceException("当前限时抢购活动不存在");
        }
        return seckill;
    }

    /**
     * 检查限时抢购参数
     * @param seckill
     * @param storeId
     */
    private void checkSeckillParam(SecKillVo seckill, String storeId) {
        seckill.checkTime();
    }
}
