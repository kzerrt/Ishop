package com.fc.ishop.controller.goods;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dos.goods.Goods;
import com.fc.ishop.dos.goods.GoodsSku;
import com.fc.ishop.dto.GoodsOperationDto;
import com.fc.ishop.enums.*;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.service.GoodsService;
import com.fc.ishop.service.GoodsSkuService;
import com.fc.ishop.dto.GoodsSearchParams;
import com.fc.ishop.vo.goods.GoodsVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.web.manager.GoodsManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/15
 */
@RestController
public class GoodsController implements GoodsManagerClient {
    // 商品
    @Autowired
    private GoodsService goodsService;
    // 规格商品
    private GoodsSkuService goodsSkuService;
    @Override
    public Page<Goods> getByPage(GoodsSearchParams goodsSearchParams) {
        return goodsService.queryByParams(goodsSearchParams);
    }

    @Override
    public ResultMessage<Page<GoodsSku>> getSkuByPage(GoodsSearchParams goodsSearchParams) {
        return ResultUtil.data(goodsSkuService.getGoodsSkuByPage(goodsSearchParams));
    }

    @Override
    public Page<Goods> getAuthPage(GoodsSearchParams goodsSearchParams) {
        goodsSearchParams.setIsAuth(GoodsAuthEnum.TOBEAUDITED.name());
        return goodsService.queryByParams(goodsSearchParams);
    }

    @Override
    public ResultMessage<Object> underGoods(String goodsId, String reason) {
        List<String> goodsIds = Arrays.asList(goodsId.split(","));
        if (Boolean.TRUE.equals(goodsService.updateGoodsMarketAble(goodsIds, GoodsStatusEnum.DOWN, reason))) {
            return ResultUtil.success();
        }
        throw new ServiceException(ResultCode.GOODS_UNDER_ERROR);
    }

    @Override
    public ResultMessage<GoodsOperationDto> save(GoodsOperationDto goodsOperationDTO) {
        goodsService.addGoods(goodsOperationDTO);
        return ResultUtil.success();
    }

    @Override
    public ResultMessage<Object> auth(List<String> goodsIds, String isAuth) {
        //校验商品是否存在
        if (goodsService.auditGoods(goodsIds, GoodsAuthEnum.valueOf(isAuth))) {
            return ResultUtil.success();
        }
        throw new ServiceException(ResultCode.GOODS_AUTH_ERROR);
    }

    @Override
    public ResultMessage<Object> unpGoods(List<String> goodsId) {
        if (goodsService.updateGoodsMarketAble(goodsId, GoodsStatusEnum.UPPER, "")) {
            return ResultUtil.success();
        }
        throw new ServiceException(ResultCode.GOODS_UPPER_ERROR);
    }

    @Override
    public ResultMessage<GoodsVo> get(String id) {
        GoodsVo goods = goodsService.getGoodsVo(id);
        return ResultUtil.data(goods);
    }

    @Override
    public ResultMessage<GoodsOperationDto> update(GoodsOperationDto goodsOperationDto, String goodsId) {
        return null;
    }

}
