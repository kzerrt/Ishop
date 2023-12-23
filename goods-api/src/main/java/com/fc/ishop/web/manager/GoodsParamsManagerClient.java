package com.fc.ishop.web.manager;

import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.goods.GoodsParameterGroupVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/12
 */
@FeignClient(value = "goods-server", contextId = "goodsParams")
public interface GoodsParamsManagerClient {

    /**
     * 通过商品id和分类id查询参数信息
     * @param goodsId 商品id
     * @param categoryId 分类id
     * @return
     */
    @GetMapping("/manager-gp/{goodsId}/{categoryId}")
    ResultMessage<List<GoodsParameterGroupVo>> getGoodsParameters(@PathVariable("goodsId") String goodsId,
                                                                  @PathVariable("categoryId") String categoryId);
}
