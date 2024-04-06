package com.fc.ishop.web.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dos.trade.SecKill;
import com.fc.ishop.dos.trade.SecKillApply;
import com.fc.ishop.dto.SecKillSearchParams;
import com.fc.ishop.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author florence
 * @date 2023/12/20
 */
@FeignClient(value = "goods-server", contextId = "secKillManager")
public interface SecKillManagerClient {

    /**
     * 添加抢购
     */
    @PostMapping("/manager-sk/add")
    ResultMessage<SecKill> addSecKill(@RequestBody SecKillVo seckillVo);

    /**
     * 更新抢购信息
     */
    @PostMapping("/manager-sk/update")
    ResultMessage<SecKill> updateSecKill(@RequestBody SecKillVo seckillVo);

    /**
     * 获取信息
     */
    @GetMapping("/manager-sk/get/{id}")
    ResultMessage<SecKill> get(@PathVariable String id);
    @PostMapping("/manager-sk/get-all")
    ResultMessage<Page<SecKillVo>> getAll(@RequestBody Map<String, String> send);
    @GetMapping("/manager-sk/del/{id}")
    ResultMessage<Object> deleteSecKill(@PathVariable String id);
    @GetMapping("/manager-sk/close/{id}")
    ResultMessage<Object> closeSecKill(@PathVariable String id);
    @GetMapping("/manager-sk/open/{id}")
    ResultMessage<Object> openSecKill(@PathVariable String id);

    /**
     * 获取申请列表
     */
    @PostMapping("/manager-sk/apply")
    ResultMessage<Page<SecKillApply>> getSecKillApply(@RequestBody Map<String, String> send);

    /**
     * 审核多个限时抢购列表
     */
    @PostMapping("/manager-sk/apply/audit/{ids}")
    ResultMessage<Object> auditSecKillApply(@PathVariable String[] ids,
                                            @RequestParam("seckillId") String seckillId,
                                            @RequestParam("applyStatus") String applyStatus,
                                            @RequestParam("failReason") String failReason);

    /**
     * ====================================      商家端     ==================================
     */
    // 获取抢购申请
    @GetMapping("/store-sa/apply/{seckillApplyId}")
    ResultMessage<SecKillApply> getSeckillApply(@PathVariable String seckillApplyId);
    /**
     * 添加活动申请
     * @param seckillId
     * @param applyVos
     * @return
     */
    @PostMapping("/store-sa/seckillApply/add/{seckillId}")
    ResultMessage<Object> addSeckillApply(@PathVariable String seckillId, @RequestBody List<SecKillApply> applyVos);

    /**
     * *************************************      买家端          *********************************************
     */
    /**
     * 获取某一时刻的商品信息
     */
    @GetMapping("/buyer/seckillApply/getSeckillGoods/{timeline}")
    List<SeckillGoodsVo> getSeckillGoods(@PathVariable Integer timeline);

    /**
     * 获取当天信息
     * @return
     */
    @GetMapping("/buyer/seckillApply/getSeckillGoods")
    List<SeckillTimelineVo> getSeckillTimeline();
}
