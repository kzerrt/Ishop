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

    /**
     * {"weight":"2","goodsGalleryFiles":[{"status":"finished","name":"download20240202142555.png","size":99628,"percentage":100,"uid":1709438100440,"showProgress":false,"response":{"success":true,"message":"success","code":200,"timestamp":1709438100419,"result":"/upload/image/MANAGER/1337306110277476352/1709438100355.png"},"url":"/upload/image/MANAGER/1337306110277476352/1709438100355.png"}],"storeCategoryPath":"","brandId":0,"goodsUnit":"个","categoryPath":"1348576427264204941,1348576427264204942,1348576427264204943,1348576427264204946,1348576427264204942,1348576427264204946,1348576427264204942,1348576427264204943","sellingPoint":"就是好","updateSku":true,"regeneratorSkuFlag":true,"categoryId":"1348576427264204943","categoryName":"手机","goodsName":"小米手机","sn":"3332221","price":"1999","cost":"1699","skuList":[{"颜色":"红色","images":[{"status":"finished","name":"download20240202142555.png","size":99628,"percentage":100,"uid":1709438100440,"showProgress":false,"response":{"success":true,"message":"success","code":200,"timestamp":1709438100419,"result":"/upload/image/MANAGER/1337306110277476352/1709438100355.png"},"url":"/upload/image/MANAGER/1337306110277476352/1709438100355.png"}],"sn":"3332223","weight":"2","quantity":"9999","cost":"1699","price":"1999"}],"goodsGalleryList":["/upload/image/MANAGER/1337306110277476352/1709438100355.png"]}
     */
    @PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
    public ResultMessage<GoodsOperationDto> save(@RequestBody GoodsOperationDto goodsOperationDTO) {
        return goodsManagerClient.save(goodsOperationDTO);
    }
}
