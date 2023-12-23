package com.fc.ishop.controller.trade;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.constant.SendParam;
import com.fc.ishop.dos.trade.SecKill;
import com.fc.ishop.dos.trade.SecKillApply;
import com.fc.ishop.dto.SecKillSearchParams;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.enums.SecKillApplyStatusEnum;
import com.fc.ishop.security.AuthUser;
import com.fc.ishop.security.context.UserContext;
import com.fc.ishop.service.SecKillApplyService;
import com.fc.ishop.service.SecKillService;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.SecKillVo;
import com.fc.ishop.web.manager.SecKillManagerClient;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 服务秒杀
 * @author florence
 * @date 2023/12/20
 */
@RestController
public class SecKillController implements SecKillManagerClient {
    @Autowired
    private SecKillService secKillService;
    @Autowired
    private SecKillApplyService secKillApplyService;
    @Override
    public ResultMessage<SecKill> addSecKill(SecKillVo seckillVo) {
        AuthUser currentUser = UserContext.getCurrentUser();
        seckillVo.setStoreId(currentUser.getId());
        seckillVo.setStoreName(currentUser.getUsername());
        seckillVo.setSeckillApplyStatus(SecKillApplyStatusEnum.NOT_APPLY.name());
        secKillService.saveSeckill(seckillVo);
        return ResultUtil.data(seckillVo);
    }

    @Override
    public ResultMessage<SecKill> updateSecKill(SecKillVo seckillVo) {
        AuthUser currentUser = UserContext.getCurrentUser();
        seckillVo.setStoreId(currentUser.getId());
        seckillVo.setStoreName(currentUser.getUsername());
        secKillService.modifySeckill(seckillVo);
        return ResultUtil.data(seckillVo);
    }

    @Override
    public ResultMessage<SecKill> get(String id) {
        return ResultUtil.data(secKillService.getById(id));
    }

    @Override
    public ResultMessage<Page<SecKillVo>> getAll(Map<String, String> send) {
        Gson gson = new Gson();
        PageVo pageVo = gson.fromJson(send.get(SendParam.pageVo), PageVo.class);
        SecKillSearchParams param = gson.fromJson(send.get(SendParam.secKillSearchParams), SecKillSearchParams.class);
        pageVo.setNotConvert(true);
        Page<SecKillVo> page = secKillService.getSeckillByPageFromMongo(param, pageVo);
        return ResultUtil.data(page);
    }

    @Override
    public ResultMessage<Object> deleteSecKill(String id) {
        secKillService.deleteSeckill(id);
        return ResultUtil.success();
    }

    @Override
    public ResultMessage<Object> closeSecKill(String id) {
        secKillService.closeSeckill(id);
        return ResultUtil.success();
    }

    @Override
    public ResultMessage<Object> openSecKill(String id) {
        secKillService.openSeckill(id);
        return ResultUtil.success();
    }

    @Override
    public ResultMessage<Page<SecKillApply>> getSecKillApply(Map<String, String> send) {
        Gson gson = new Gson();
        SecKillSearchParams param = gson.fromJson(send.get(SendParam.secKillSearchParams), SecKillSearchParams.class);
        PageVo pageVo = gson.fromJson(send.get(SendParam.pageVo), PageVo.class);
        Page<SecKillApply> seckillApply = secKillApplyService.getSeckillApplyFromMongo(param, pageVo);
        return ResultUtil.data(seckillApply);
    }

    @Override
    public ResultMessage<Object> auditSecKillApply(String[] ids, String seckillId, String applyStatus, String failReason) {
        secKillApplyService.auditBatchApply(ids, seckillId, applyStatus, failReason);
        return ResultUtil.success();
    }

    /**
     * +++++++++++++++++++++++++++++++++++++++++商家端+++++++++++++++++++++++++++++++++++++++++++++++++++++
     */

    @Override
    public ResultMessage<SecKillApply> getSeckillApply(String seckillApplyId) {
        return ResultUtil.data(secKillApplyService.getById(seckillApplyId));
    }

    @Override
    public ResultMessage<Object> addSeckillApply(String seckillId, List<SecKillApply> applyVos) {
        AuthUser currentUser = UserContext.getCurrentUser();

        secKillApplyService.addSeckillApply(seckillId, currentUser.getStoreId(), applyVos);
        return ResultUtil.success();
    }




}
