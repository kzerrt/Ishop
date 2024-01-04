package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.goods.GoodsParams;
import com.fc.ishop.vo.goods.GoodsParameterGroupVo;

import java.util.List;

/**
 * 商品关联参数
 * @author florence
 * @date 2023/12/12
 */
public interface GoodsParamsService extends IService<GoodsParams> {
    /**
     * 查询商品参数信息
     *
     * @param goodsId    商品id
     * @param categoryId 分了id
     * @return 商品参数信息
     */
    List<GoodsParameterGroupVo> queryGoodsParams(String goodsId, String categoryId);
    /**
     * 获取商品关联参数
     *
     * @param goodsId 商品ID
     * @return 商品关联参数
     */
    List<GoodsParams> getGoodsParamsByGoodsId(String goodsId);

    void addParams(List<GoodsParams> goodsParamsList, String id);
}
