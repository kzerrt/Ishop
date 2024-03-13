package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.goods.Goods;
import com.fc.ishop.dto.GoodsOperationDto;
import com.fc.ishop.enums.GoodsAuthEnum;
import com.fc.ishop.enums.GoodsStatusEnum;
import com.fc.ishop.dto.GoodsSearchParams;
import com.fc.ishop.vo.goods.GoodsVo;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/11
 */
public interface GoodsService extends IService<Goods> {
    /**
     * 获取今日上架商品
     * @return
     */
    Integer todayUpperNum();
    /**
     * 统计商品数量
     * @param goodsStatusEnum
     * @param goodsAuthEnum
     * @return
     */
    int goodsNum(String goodsStatusEnum, String goodsAuthEnum);
    /**
     * 根据分类id查询商品数量
     * @param categoryId
     * @return
     */
    int getGoodsCountByCategory(String categoryId);

    /**
     * 商品查询
     * @param goodsSearchParams
     * @return
     */
    Page<Goods> queryByParams(GoodsSearchParams goodsSearchParams);

    /**
     * 更新商品上架状态
     * @param goodsIds 商品id
     * @param goodsStatusEnum 更新的状态
     * @param underReason 下架原因
     * @return
     */
    boolean updateGoodsMarketAble(List<String> goodsIds, GoodsStatusEnum goodsStatusEnum, String underReason);
    /**
     * 批量审核商品
     *
     * @param goodsIds      商品id列表
     * @param goodsAuthEnum 审核操作
     * @return 审核结果
     */
    boolean auditGoods(List<String> goodsIds, GoodsAuthEnum goodsAuthEnum);

    GoodsVo getGoodsVo(String id);

    /**
     * 下架所有商品
     * @param storeId 店铺id
     */
    void underStoreGoods(String storeId);

    void addGoods(GoodsOperationDto goodsOperationDTO);
}
