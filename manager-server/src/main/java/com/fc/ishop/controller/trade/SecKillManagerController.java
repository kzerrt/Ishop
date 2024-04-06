package com.fc.ishop.controller.trade;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.constant.SendParam;
import com.fc.ishop.dos.trade.SecKill;
import com.fc.ishop.dos.trade.SecKillApply;
import com.fc.ishop.dto.SecKillSearchParams;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.SecKillVo;
import com.fc.ishop.web.manager.SecKillManagerClient;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author florence
 * @date 2023/12/21
 */
@RestController
@RequestMapping("/promotion/seckill")
public class SecKillManagerController {
    @Autowired
    private SecKillManagerClient secKillManagerClient;
    @PostMapping
    //(value = "添加限时抢购")
    public ResultMessage<SecKill> addSeckill(SecKillVo seckillVO) {
        return secKillManagerClient.addSecKill(seckillVO);
    }

    @PutMapping
    //(value = "修改限时抢购")
    public ResultMessage<SecKill> updateSeckill(SecKillVo seckillVO) {
        return secKillManagerClient.updateSecKill(seckillVO);
    }

    @GetMapping(value = "/{id}")
    public ResultMessage<SecKill> get(@PathVariable String id) {
        return secKillManagerClient.get(id);
    }

    @GetMapping
    //(value = "分页查询限时抢购列表")
    public ResultMessage<Page<SecKillVo>> getAll(SecKillSearchParams param, PageVo pageVo) {
        pageVo.setNotConvert(true);
        Gson gson = new Gson();
        Map<String, String> send = new HashMap<>();
        send.put(SendParam.secKillSearchParams, gson.toJson(param));
        send.put(SendParam.pageVo, gson.toJson(pageVo));
        return secKillManagerClient.getAll(send);
    }

    @DeleteMapping("/{id}")
    //(value = "删除一个限时抢购")
    public ResultMessage<Object> deleteSeckill(@PathVariable String id) {
        return secKillManagerClient.deleteSecKill(id);
    }

    @PutMapping("/close/{id}")
    //(value = "关闭一个限时抢购")
    public ResultMessage<Object> closeSeckill(@PathVariable String id) {
        return secKillManagerClient.closeSecKill(id);
    }

    @PutMapping("/open/{id}")
    //(value = "一个限时抢购")
    public ResultMessage<Object> openSeckill(@PathVariable String id) {
        return secKillManagerClient.openSecKill(id);
    }

    @GetMapping("/apply")
    //(value = "获取限时抢购申请列表")
    public ResultMessage<Page<SecKillApply>> getSeckillApply(SecKillSearchParams param, PageVo pageVo) {
        Gson gson = new Gson();
        Map<String, String> send = new HashMap<>();
        send.put(SendParam.secKillSearchParams, gson.toJson(param));
        send.put(SendParam.pageVo, gson.toJson(pageVo));
        return secKillManagerClient.getSecKillApply(send);
    }
    @PostMapping("/apply/{seckillId}")
    public ResultMessage<String> addSecKillApply(@PathVariable String seckillId,@RequestBody List<SecKillApply> secKillApplyList) {
        if (seckillId == null || secKillApplyList == null || secKillApplyList.isEmpty()) {
            return ResultUtil.error(ResultCode.POINT_GOODS_ERROR);
        }
        secKillManagerClient.addSeckillApply(seckillId, secKillApplyList);
        return ResultUtil.success();
    }

    @PutMapping("/apply/audit/{ids}")
    //(value = "审核多个限时抢购申请")
    public ResultMessage<Object> auditSeckillApply(@PathVariable String[] ids, String seckillId, String applyStatus, String failReason) {
        return secKillManagerClient.auditSecKillApply(ids, seckillId, applyStatus, failReason);
    }
}
