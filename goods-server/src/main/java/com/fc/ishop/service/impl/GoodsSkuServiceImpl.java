package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.goods.Goods;
import com.fc.ishop.dos.goods.GoodsSku;
import com.fc.ishop.enums.GoodsAuthEnum;
import com.fc.ishop.enums.GoodsStatusEnum;
import com.fc.ishop.mapper.GoodsSkuMapper;
import com.fc.ishop.service.GoodsSkuService;
import com.fc.ishop.utils.PageUtil;
import com.fc.ishop.dto.GoodsSearchParams;
import com.fc.ishop.vo.goods.GoodsSkuVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/15
 */
@Service("goodsSkuService")
public class GoodsSkuServiceImpl
        extends ServiceImpl<GoodsSkuMapper, GoodsSku> implements GoodsSkuService {
    @Override
    public Page<GoodsSku> getGoodsSkuByPage(GoodsSearchParams searchParams) {
        return this.page(PageUtil.initPage(searchParams), searchParams.queryWrapper());
    }

    @Override
    public void updateGoodsSkuStatus(Goods goods) {
        LambdaUpdateWrapper<GoodsSku> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(GoodsSku::getGoodsId, goods.getId());
        updateWrapper.set(GoodsSku::getMarketEnable, goods.getMarketEnable());
        updateWrapper.set(GoodsSku::getIsAuth, goods.getIsAuth());
        this.update(updateWrapper);
    }

    @Override
    public void updateGoodsSkuStatusByIds(List<String> ids, GoodsStatusEnum goodsStatusEnum) {
        LambdaUpdateWrapper<GoodsSku> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(GoodsSku::getGoodsId, ids);
        updateWrapper.set(GoodsSku::getMarketEnable, goodsStatusEnum.name());
        this.update(updateWrapper);
    }

    @Override
    public void updateAuthStatusByIds(List<String> ids, GoodsAuthEnum goodsAuthEnum) {
        LambdaUpdateWrapper<GoodsSku> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(GoodsSku::getGoodsId, ids);
        updateWrapper.set(GoodsSku::getIsAuth, goodsAuthEnum.name());
        this.update(updateWrapper);
    }

    @Override
    public List<GoodsSkuVo> getGoodsListByGoodsId(String goodsId) {
        List<GoodsSku> list = this.list(new LambdaQueryWrapper<GoodsSku>().eq(GoodsSku::getGoodsId, goodsId));
        return this.getGoodsSkuVoList(list);
    }
    @Override
    public List<GoodsSkuVo> getGoodsSkuVoList(List<GoodsSku> list) {
        List<GoodsSkuVo> goodsSkuVOS = new ArrayList<>();
        for (GoodsSku goodsSku : list) {
            GoodsSkuVo goodsSkuVO = this.getGoodsSkuVo(goodsSku);
            goodsSkuVOS.add(goodsSkuVO);
        }
        return goodsSkuVOS;
    }

    @Override
    public GoodsSku getGoodsSkuByIdFromCache(String skuId) {
        return null;
    }

    // todo 商品展示信息拼装
    private GoodsSkuVo getGoodsSkuVo(GoodsSku goodsSku) {
        return null;
    }
}
