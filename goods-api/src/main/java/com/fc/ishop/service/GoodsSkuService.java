package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.goods.Goods;
import com.fc.ishop.dos.goods.GoodsSku;
import com.fc.ishop.enums.GoodsAuthEnum;
import com.fc.ishop.enums.GoodsStatusEnum;
import com.fc.ishop.dto.GoodsSearchParams;
import com.fc.ishop.vo.goods.GoodsSkuVo;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/15
 */
public interface GoodsSkuService extends IService<GoodsSku> {
    /**
     * 分页查询商品sku信息
     *
     * @param searchParams 查询参数
     * @return 商品sku信息
     */
    Page<GoodsSku> getGoodsSkuByPage(GoodsSearchParams searchParams);

    /**
     * 修改商品sku状态
     * @param goods
     */
    void updateGoodsSkuStatus(Goods goods);

    /**
     * 修改状态
     * @param ids 商品id集合
     * @param goodsStatusEnum 状态
     */
    void updateGoodsSkuStatusByIds(List<String> ids, GoodsStatusEnum goodsStatusEnum);
    void updateAuthStatusByIds(List<String> ids, GoodsAuthEnum goodsAuthEnum);

    /**
     * 获取goodsId下所有的goodsSku
     *
     * @param goodsId 商品id
     * @return goodsSku列表
     */
    List<GoodsSkuVo> getGoodsListByGoodsId(String goodsId);
    /**
     * 根据goodsSku组装goodsSkuVO
     *
     * @param list 商品id
     * @return goodsSku列表
     */
    List<GoodsSkuVo> getGoodsSkuVoList(List<GoodsSku> list);

    GoodsSku getGoodsSkuByIdFromCache(String skuId);
}
