package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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

    @Override
    public void add(List<String> goodsGalleryList, String goodsId) {
        //删除原来商品相册信息
        baseMapper.delete(new UpdateWrapper<GoodsGallery>().eq("goods_id", goodsId));
        //确定好图片选择器后进行处理
        int i = 0;
        for (String origin : goodsGalleryList) {
            // 获取带所有缩略的相册
            GoodsGallery galley = this.getGoodsGallery(origin);
            galley.setGoodsId(goodsId);
            // 默认第一个为默认图片
            galley.setIsDefault(i == 0 ? 1 : 0);
            i++;
            baseMapper.insert(galley);
        }
    }

    private GoodsGallery getGoodsGallery(String origin) {
        GoodsGallery goodsGallery = new GoodsGallery();
        goodsGallery.setOriginal(origin);
        goodsGallery.setSmall(origin);
        goodsGallery.setThumbnail(origin);
        return goodsGallery;
    }
}
