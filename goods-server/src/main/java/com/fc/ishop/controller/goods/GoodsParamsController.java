package com.fc.ishop.controller.goods;

import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.service.GoodsParamsService;
import com.fc.ishop.vo.goods.GoodsParameterGroupVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.web.manager.GoodsParamsManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/12
 */
@RestController
public class GoodsParamsController implements GoodsParamsManagerClient {
    @Autowired
    private GoodsParamsService goodsParamsService;
    @Override
    public ResultMessage<List<GoodsParameterGroupVo>> getGoodsParameters(String goodsId, String categoryId) {
        return ResultUtil.data(goodsParamsService.queryGoodsParams(goodsId, categoryId));
    }
}
