package com.fc.ishop.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.goods.GoodsSku;
import com.fc.ishop.dos.trade.PromotionGoods;
import com.fc.ishop.dto.PromotionGoodsDto;
import com.fc.ishop.enums.PromotionTypeEnum;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.mapper.PromotionGoodsMapper;
import com.fc.ishop.service.GoodsSkuService;
import com.fc.ishop.service.PromotionGoodsService;
import com.fc.ishop.utils.BeanUtil;
import com.fc.ishop.utils.PageUtil;
import com.fc.ishop.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/21
 */
@Service("promotionGoodsService")
public class PromotionGoodsServiceImpl
        extends ServiceImpl<PromotionGoodsMapper, PromotionGoods> implements PromotionGoodsService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private PromotionGoodsService promotionGoodsService;
    @Autowired
    private GoodsSkuService goodsSkuService;
    @Override
    public Integer getPromotionGoodsStock(PromotionTypeEnum typeEnum, String promotionId, String skuId) {
        // 商品库存添加
        String promotionStockKey = PromotionGoodsService.getPromotionGoodsStockCacheKey(typeEnum, promotionId, skuId);
        String promotionGoodsStock = stringRedisTemplate.opsForValue().get(promotionStockKey);

        PromotionGoods promotionGoods = this.getPromotionGoods(typeEnum, promotionId, skuId);
        if (promotionGoods == null) {
            throw new ServiceException("当前促销商品不存在！");
        }
        if (promotionGoodsStock != null && CharSequenceUtil.isNotEmpty(promotionGoodsStock) && promotionGoods.getQuantity() == Integer.parseInt(promotionGoodsStock)) {
            return Integer.parseInt(promotionGoodsStock);
        } else {
            stringRedisTemplate.opsForValue().set(promotionStockKey, promotionGoods.getQuantity().toString());
            return promotionGoods.getQuantity();
        }
    }

    @Override
    public Page<PromotionGoodsDto> getCurrentPromotionGoods(String promotionType, PageVo pageVo) {
        Page<PromotionGoodsDto> promotionGoodsPage = new Page<>();
        promotionGoodsPage.setSize(pageVo.getPageSize());
        promotionGoodsPage.setCurrent(pageVo.getPageNumber());
        Date now = new Date();
        Query query = new Query();
        query.addCriteria(Criteria.where("startTime").lt(now));
        query.addCriteria(Criteria.where("endTime").gt(now));
        List<PromotionGoodsDto> promotionGoodsDTOList = new ArrayList<>();
        int total = 0;
        // 根据不同类型获取不同数据
        switch (PromotionTypeEnum.valueOf(promotionType)) {
            case SECKILL:

        }
        return promotionGoodsPage;
    }

    public PromotionGoods getPromotionGoods(PromotionTypeEnum typeEnum, String promotionId, String skuId) {
        LambdaQueryWrapper<PromotionGoods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PromotionGoods::getPromotionType, typeEnum.name()).eq(PromotionGoods::getPromotionId, promotionId).eq(PromotionGoods::getSkuId, skuId);
        return this.getOne(queryWrapper);
    }
    private Page<PromotionGoodsDto> getGoodsSkuToPromotionGoodsByPage(String promotionType, PageVo pageVo) {
        Date date = new Date();
        LambdaQueryWrapper<PromotionGoods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PromotionGoods::getPromotionType, promotionType)
                .le(PromotionGoods::getStartTime, date).ge(PromotionGoods::getEndTime, date);
        List<PromotionGoods> goods = promotionGoodsService.list(queryWrapper);
        // 将数据装配
        Page<PromotionGoodsDto> page = PageUtil.initPage(pageVo);
        List<PromotionGoodsDto> list = new ArrayList<>(goods.size());
        for (PromotionGoods good : goods) {
            list.add(this.convertPromotionGoods(good));
        }
        page.setTotal(goods.size());
        page.setRecords(list);
        return page;
    }
    private PromotionGoodsDto convertPromotionGoods(PromotionGoods promotionGoods) {
        PromotionGoodsDto promotionGoodsDto = new PromotionGoodsDto();
        GoodsSku goodsSku = goodsSkuService.getById(promotionGoods.getSkuId());
        BeanUtil.copyProperties(promotionGoods, promotionGoodsDto);
        if (goodsSku != null) {
            promotionGoodsDto.setGoodsId(goodsSku.getGoodsId());
            promotionGoodsDto.setOriginPrice(goodsSku.getPrice());
            promotionGoodsDto.setGoodsName(goodsSku.getGoodsName());
            promotionGoodsDto.setGoodsImage(goodsSku.getThumbnail());
        }
        return promotionGoodsDto;
    }

}
