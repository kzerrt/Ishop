package com.fc.ishop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fc.ishop.dos.goods.Goods;
import org.apache.ibatis.annotations.Update;

/**
 * @author florence
 * @date 2023/12/11
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    @Update("update li_goods set market_enable = 0 WHERE store_id = #{storeId}")
    void underStoreGoods(String storeId);
}
