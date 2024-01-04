package com.fc.ishop.web.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dos.goods.Goods;
import com.fc.ishop.dos.goods.GoodsSku;
import com.fc.ishop.dto.GoodsOperationDto;
import com.fc.ishop.dto.GoodsSearchParams;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.goods.GoodsVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 管理端 商品管理
 * @author florence
 * @date 2023/12/15
 */
@FeignClient(value = "goods-server", contextId = "goods")
public interface GoodsManagerClient {

    /**
     * 分页获取
     */
    @PostMapping("/manager-g/list")
    Page<Goods> getByPage(@RequestBody GoodsSearchParams goodsSearchParams);

    /**
     * 分页获取商品列表
     * @param goodsSearchParams
     * @return
     */
    @PostMapping("/manager-g/sku/list")
    ResultMessage<Page<GoodsSku>> getSkuByPage(@RequestBody GoodsSearchParams goodsSearchParams);

    /**
     * 分页获取待审核商品
     * @param goodsSearchParams
     * @return
     */
    @PostMapping("/manager-g/auth/list")
    Page<Goods> getAuthPage(@RequestBody GoodsSearchParams goodsSearchParams);

    /**
     * 管理员下架商品
     * @param goodsId
     * @param reason
     * @return
     */
    @GetMapping("/manager-g/{goodsId}/under")
    ResultMessage<Object> underGoods(@PathVariable String goodsId,
                                     @NotEmpty(message = "下架原因不能为空")
                                     @RequestParam String reason);

    /**
     * 添加商品
     */
    @PostMapping("/manager-g/saveGoods")
    ResultMessage<GoodsOperationDto> save(@RequestBody GoodsOperationDto goodsOperationDTO);
    /**
     * 管理员审核商品
     * @param goodsIds
     * @param isAuth
     * @return
     */
    @GetMapping("/manager-g/{goodsIds}/auth")
    ResultMessage<Object> auth(@PathVariable List<String> goodsIds, @RequestParam String isAuth);

    /**
     * 管理员上架商品
     * @param goodsId
     * @return
     */
    @GetMapping("/manager-g/{goodsId}/up")
    ResultMessage<Object> unpGoods(@PathVariable List<String> goodsId);

    /**
     * 通过id获取商品信息
     * @param id
     * @return
     */
    @GetMapping("/manager-g/get/{id}")
    ResultMessage<GoodsVo> get(@PathVariable String id);
}
