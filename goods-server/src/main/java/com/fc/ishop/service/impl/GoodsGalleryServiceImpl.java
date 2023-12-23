package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.goods.GoodsGallery;
import com.fc.ishop.mapper.GoodsGalleryMapper;
import com.fc.ishop.service.GoodsGalleryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/15
 */
@Service("goodsGalleryService")
public class GoodsGalleryServiceImpl
        extends ServiceImpl<GoodsGalleryMapper, GoodsGallery> implements GoodsGalleryService {
    @Override
    public List<GoodsGallery> getGoodsListByGoodsId(String goodsId) {
        //根据商品id查询商品相册
        return baseMapper.selectList(new QueryWrapper<GoodsGallery>().eq("goods_id", goodsId));
    }
}
