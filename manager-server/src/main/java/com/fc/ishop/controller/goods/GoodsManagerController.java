package com.fc.ishop.controller.goods;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dos.goods.Goods;
import com.fc.ishop.dos.goods.GoodsSku;
import com.fc.ishop.dto.GoodsOperationDto;
import com.fc.ishop.dto.GoodsSearchParams;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.goods.GoodsVo;
import com.fc.ishop.web.manager.GoodsManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/15
 */
@RestController
@RequestMapping("/goods")
public class GoodsManagerController {
    @Autowired
    private GoodsManagerClient goodsManagerClient;

    /**
     * 分页获取
     */
    @GetMapping(value = "/list")
    public Page<Goods> getByPage(GoodsSearchParams goodsSearchParams) {
        return goodsManagerClient.getByPage(goodsSearchParams);
    }
    /**
     *分页获取商品列表
     */
    @GetMapping(value = "/sku/list")
    public ResultMessage<Page<GoodsSku>> getSkuByPage(GoodsSearchParams goodsSearchParams) {
        return goodsManagerClient.getSkuByPage(goodsSearchParams);
    }
    /**
     *分页获取待审核商品
     */
    @GetMapping(value = "/auth/list")
    public Page<Goods> getAuthPage(GoodsSearchParams goodsSearchParams) {
        return goodsManagerClient.getAuthPage(goodsSearchParams);
    }
    /**
     *管理员下架商品
     */
    @PutMapping(value = "/{goodsId}/under")
    public ResultMessage<Object> underGoods(@PathVariable String goodsId, @NotEmpty(message = "下架原因不能为空") @RequestParam String reason) {
        return goodsManagerClient.underGoods(goodsId, reason);
    }
    /**
     *管理员审核商品
     */
    @PutMapping(value = "{goodsIds}/auth")
    public ResultMessage<Object> auth(@PathVariable List<String> goodsIds, @RequestParam String isAuth) {
        return goodsManagerClient.auth(goodsIds, isAuth);
    }

    /**
     *管理员上架商品
     */
    @PutMapping(value = "/{goodsId}/up")
    public ResultMessage<Object> unpGoods(@PathVariable List<String> goodsId) {
        return goodsManagerClient.unpGoods(goodsId);
    }

    /**
     *通过id获取商品详情
     */
    @GetMapping(value = "/get/{id}")
    public ResultMessage<GoodsVo> get(@PathVariable String id) {
        return goodsManagerClient.get(id);
    }

    //(value = "修改商品")
    @PutMapping(value = "/update/{goodsId}", consumes = "application/json", produces = "application/json")
    public ResultMessage<GoodsOperationDto> update(@RequestBody GoodsOperationDto goodsOperationDTO, @PathVariable String goodsId) {
        return goodsManagerClient.update(goodsOperationDTO, goodsId);
    }
    @PutMapping(value = "/save", consumes = "application/json", produces = "application/json")
    public ResultMessage<GoodsOperationDto> save(@RequestBody GoodsOperationDto goodsOperationDTO) {
        return goodsManagerClient.save(goodsOperationDTO);
    }
}
