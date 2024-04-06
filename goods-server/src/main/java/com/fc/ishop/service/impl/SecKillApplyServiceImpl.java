package com.fc.ishop.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.goods.Goods;
import com.fc.ishop.dos.goods.GoodsSku;
import com.fc.ishop.dos.trade.PromotionGoods;
import com.fc.ishop.dos.trade.SecKill;
import com.fc.ishop.dos.trade.SecKillApply;
import com.fc.ishop.dto.SecKillSearchParams;
import com.fc.ishop.enums.PromotionApplyStatusEnum;
import com.fc.ishop.enums.PromotionStatusEnum;
import com.fc.ishop.enums.PromotionTypeEnum;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.mapper.SecKillApplyMapper;
import com.fc.ishop.service.*;
import com.fc.ishop.util.MongoUtil;
import com.fc.ishop.util.PromotionTools;
import com.fc.ishop.utils.DateUtil;
import com.fc.ishop.utils.StringUtils;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.SecKillVo;
import com.fc.ishop.vo.SeckillGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author florence
 * @date 2023/12/20
 */
@Service("secKillApplyService")
public class SecKillApplyServiceImpl
        extends ServiceImpl<SecKillApplyMapper, SecKillApply> implements SecKillApplyService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private PromotionGoodsService promotionGoodsService;
    @Autowired
    private GoodsSkuService goodsSkuService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SecKillService secKillService;
    @Override
    public Page<SecKillApply> getSeckillApplyFromMongo(SecKillSearchParams queryParam, PageVo pageVo) {
        Page<SecKillApply> page = new Page<>();
        Query query = MongoUtil.creatMongoQuery(queryParam);
        SecKillVo secKillVo = this.mongoTemplate.findOne(query, SecKillVo.class);
        if (secKillVo == null || pageVo == null) {
            return null;
        }
        page.setCurrent(pageVo.getMongoPageNumber());
        page.setSize(pageVo.getPageSize());
        List<SecKillApply> seckillApplyList = secKillVo.getSeckillApplyList() != null ? secKillVo.getSeckillApplyList() : new ArrayList<>();
        for (SecKillApply seckillApply : seckillApplyList) {
            if (CharSequenceUtil.isNotEmpty(queryParam.getStoreId()) && !seckillApply.getStoreId().equals(queryParam.getStoreId())) {
                seckillApplyList.remove(seckillApply);
            }
            try {
                Integer goodsStock = promotionGoodsService.getPromotionGoodsStock(PromotionTypeEnum.SECKILL, seckillApply.getSeckillId(), seckillApply.getSkuId());
                seckillApply.setQuantity(goodsStock);
            } catch (Exception e) {
                log.error("获取促销商品促销失败！", e);
            }
        }
        page.setTotal(seckillApplyList.size());
        List<SecKillApply> data = CollUtil.page(pageVo.getMongoPageNumber(), pageVo.getPageSize(), seckillApplyList);
        page.setRecords(data);
        return page;
    }

    @Override
    public void auditBatchApply(String[] ids, String seckillId, String applyStatus, String failReason) {
        if (ids == null || ids.length <= 0) {
            throw new ServiceException("请提供要审核的商品");
        }
        if (StringUtils.isEmpty(applyStatus) || PromotionApplyStatusEnum.valueOf(applyStatus).name().isEmpty()) {
            throw new ServiceException("提供的审批状态值不正确");
        }
        if (PromotionApplyStatusEnum.REFUSE.name().equals(applyStatus)) {
            boolean isEmpty = StringUtils.isEmpty(failReason);
            if (isEmpty) {
                throw new ServiceException("在驳回状态下驳回原因必填");
            }
        }

        SecKillVo seckillVO = this.mongoTemplate.findById(seckillId, SecKillVo.class);
        if (seckillVO == null) {
            log.error("编号为【" + seckillId + "】限时抢购请不存在");
            throw new ServiceException();
        }
        List<SecKillApply> seckillApplyList = seckillVO.getSeckillApplyList();

        for (String id : ids) {

            Optional<SecKillApply> seckillApplyOptional = seckillApplyList.stream().filter(i -> i.getId().equals(id)).findFirst();
            SecKillApply seckillApply;
            if (seckillApplyOptional.isPresent()) {
                seckillApply = seckillApplyOptional.get();
            } else {
                log.error("编号为【" + id + "】限时抢购的申请不存在");
                throw new ServiceException();
            }

            seckillApply.setPromotionApplyStatus(PromotionApplyStatusEnum.valueOf(applyStatus).name());
            seckillApply.setFailReason(failReason);
            this.updateById(seckillApply);
        }
        seckillVO.setSeckillApplyList(seckillApplyList);
        mongoTemplate.save(seckillVO);
    }

    /**
     * ====================================  商家端    ===============================
     */
    @Override
    public void addSeckillApply(String seckillId, String storeId, List<SecKillApply> applyVos) {
        SecKillVo seckill = mongoTemplate.findById(seckillId, SecKillVo.class);
        if (seckill == null) {
            throw new ServiceException("当前参与的限时抢购不存在！");
        }
        seckill.checkTime();

        List<SecKillApply> originList = seckill.getSeckillApplyList();
        List<PromotionGoods> promotionGoodsList = new ArrayList<>();
        if (originList == null) {
            originList = new ArrayList<>();
        }
        for (SecKillApply seckillApply : applyVos) {
            Goods goods = goodsService.getById(seckillApply.getSkuId());
            seckillApply.setOriginalPrice(goods.getPrice());
            seckillApply.setPromotionApplyStatus(PromotionApplyStatusEnum.APPLY.name());
            seckillApply.setSalesNum(0);

            Optional<SecKillApply> first = originList.stream().filter(i -> i.getSkuId().equals(seckillApply.getSkuId())).findFirst();
            if (first.isPresent() && (first.get().getPromotionApplyStatus().equals(PromotionApplyStatusEnum.REFUSE.name()) || first.get().getPromotionApplyStatus().equals(PromotionApplyStatusEnum.APPLY.name()))) {
                originList = originList.stream().filter(i -> !i.getSkuId().equals(seckillApply.getSkuId())).collect(Collectors.toList());
            } else if (first.isPresent() && first.get().getPromotionApplyStatus().equals(PromotionApplyStatusEnum.PASS.name())) {
                continue;
            }

            originList.add(seckillApply);
            PromotionGoods promotionGoods = new PromotionGoods(goods);
            promotionGoods.setPrice(seckillApply.getPrice());
            promotionGoods.setQuantity(seckillApply.getQuantity());
            promotionGoods.setStartTime(seckill.getStartTime());
            promotionGoods.setEndTime(seckill.getEndTime());
            promotionGoods.setPromotionId(seckillId);
            promotionGoods.setPromotionType(PromotionTypeEnum.SECKILL.name());

            promotionGoodsList.add(promotionGoods);
        }
        this.saveOrUpdateBatch(originList);
        seckill.setSeckillApplyList(originList);
        this.mongoTemplate.save(seckill);

        if (!promotionGoodsList.isEmpty()) {
            LambdaQueryWrapper<PromotionGoods> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(PromotionGoods::getSkuId, promotionGoodsList.stream().map(PromotionGoods::getSkuId).collect(Collectors.toList())).eq(PromotionGoods::getStoreId, storeId);
            promotionGoodsService.remove(queryWrapper);
            PromotionTools.promotionGoodsInit(promotionGoodsList, seckill, PromotionTypeEnum.SECKILL);
            promotionGoodsService.saveBatch(promotionGoodsList);
        }
    }

    @Override
    public List<SeckillGoodsVo> getSeckillGoods(Integer timeline) {
        if (timeline == null) {
            return null;
        }
        List<SeckillGoodsVo> list = new ArrayList<>();
        LambdaQueryWrapper<SecKill> secKillWrapper = new LambdaQueryWrapper<>();
        secKillWrapper.gt(SecKill::getStartTime, DateUtil.startOfTodDay() * 1000)
                .lt(SecKill::getEndTime, DateUtil.endOfDate())
                .and(i -> i.eq(SecKill::getPromotionStatus, PromotionStatusEnum.NEW.name())
                        .or(j -> j.eq(SecKill::getPromotionStatus, PromotionStatusEnum.START.name())));
        List<SecKill> secKills = secKillService.list(secKillWrapper);
        if (secKills.isEmpty()) {
            return list;
        }
        for (SecKill secKill : secKills) {
            LambdaQueryWrapper<SecKillApply> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SecKillApply::getTimeLine, timeline).eq(SecKillApply::getSeckillId, secKill.getId())
                    .eq(SecKillApply::getPromotionApplyStatus, PromotionApplyStatusEnum.PASS.name());
            List<SecKillApply> killApplies = this.list(queryWrapper);
            for (SecKillApply killApply : killApplies) {
                Goods goods = goodsService.getById(killApply.getSkuId());
                if (goods != null) {
                    SeckillGoodsVo seckillGoodsVo = new SeckillGoodsVo(goods);
                    seckillGoodsVo.setSeckillId(secKill.getId());
                    list.add(seckillGoodsVo);
                }
            }
        }
        return list;
    }

    /**
     * 检查限时抢购申请列表参数信息
     *
     * @param applyEndTime     申请结束时间
     * @param hours            限时抢购时间段
     * @param seckillApplyList 限时抢购申请列表
     * @param storeId          当前申请商家编号
     */
    private void checkSeckillApplyList(Long applyEndTime, String hours, List<SecKillApply> seckillApplyList, String storeId) {
        List<String> existSku = new ArrayList<>();
        for (SecKillApply seckillApply : seckillApplyList) {
            seckillApply.setStoreId(storeId);
            if (seckillApply.getPrice() > seckillApply.getOriginalPrice()) {
                throw new ServiceException("活动价格不能大于商品原价");
            }

            // 检查限时抢购申请的时刻，是否存在在限时抢购的时间段内
            String[] rangeHours = hours.split(",");
            boolean containsSame = Arrays.stream(rangeHours).anyMatch(i -> i.equals(seckillApply.getTimeLine().toString()));
            if (!containsSame) {
                throw new ServiceException("时刻参数异常");
            }

            if (existSku.contains(seckillApply.getSkuId())) {
                throw new ServiceException(seckillApply.getGoodsName() + "该商品不能同时参加多个时间段的活动");
            } else {
                existSku.add(seckillApply.getSkuId());
            }

        }
    }
}
