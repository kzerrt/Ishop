package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.goods.GoodsGallery;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/15
 */
public interface GoodsGalleryService extends IService<GoodsGallery> {
    /**
     * 根据商品 id查询商品相册原图
     *
     * @param goodsId 商品ID
     * @return 商品相册列表
     */
    List<GoodsGallery> getGoodsListByGoodsId(String goodsId);
}
