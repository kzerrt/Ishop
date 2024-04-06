package com.fc.ishop.controller.trade;

import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.SeckillGoodsVo;
import com.fc.ishop.vo.SeckillTimelineVo;
import com.fc.ishop.web.manager.SecKillManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author florence
 * @date 2024/4/5
 */
@RestController
@RequestMapping("/buyer/seckillApply/getSeckillGoods/{timeline}")
public class SeKillBuyerController {
    @Autowired
    private SecKillManagerClient secKillManagerClient;
    //(value = "获取当天限时抢购信息")
    @GetMapping
    public ResultMessage<List<SeckillTimelineVo>> getSeckillTime() {
        return ResultUtil.data(secKillManagerClient.getSeckillTimeline());
    }

    //(value = "获取某个时刻的限时抢购商品信息")
    @GetMapping("/{timeline}")
    public ResultMessage<List<SeckillGoodsVo>> getSeckillGoods(@PathVariable Integer timeline) {
        return ResultUtil.data(secKillManagerClient.getSeckillGoods(timeline));
    }
}
