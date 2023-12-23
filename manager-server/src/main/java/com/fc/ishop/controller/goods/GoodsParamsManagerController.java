package com.fc.ishop.controller.goods;

import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.goods.GoodsParameterGroupVo;
import com.fc.ishop.web.manager.GoodsParamsManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 管理端 商品关联参数接口
 * @author florence
 * @date 2023/12/12
 */
@RestController
@RequestMapping("/goods/parameters")
public class GoodsParamsManagerController {
    @Autowired
    private GoodsParamsManagerClient goodsParamsManagerClient;

    /**
     * 通过商品id和分类id查询参数信息
     * @param goodsId
     * @param categoryId
     * @return
     */
    @GetMapping(value = "/{goodsId}/{categoryId}")
    public ResultMessage<List<GoodsParameterGroupVo>> getGoodsParameters(@PathVariable String goodsId, @PathVariable String categoryId) {
        return goodsParamsManagerClient.getGoodsParameters(goodsId, categoryId);
    }
}
