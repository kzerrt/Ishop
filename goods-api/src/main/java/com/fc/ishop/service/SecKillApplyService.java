package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.trade.SecKillApply;
import com.fc.ishop.dto.SecKillSearchParams;
import com.fc.ishop.vo.PageVo;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/20
 */
public interface SecKillApplyService extends IService<SecKillApply> {
    /**
     * 从mongo中分页查询限时请购申请列表
     *
     * @param queryParam 限时抢购申请查询参数
     * @param pageVo     分页参数
     * @return 限时请购申请列表
     */
    Page<SecKillApply> getSeckillApplyFromMongo(SecKillSearchParams queryParam, PageVo pageVo);

    /**
     * 批量审核请求
     * @param ids
     * @param seckillId
     * @param applyStatus
     * @param failReason
     */
    void auditBatchApply(String[] ids, String seckillId, String applyStatus, String failReason);

    /**
     * 添加抢购申请
     * @param seckillId 抢购活动id
     * @param storeId 店铺id
     * @param applyVos 请求类
     */
    void addSeckillApply(String seckillId, String storeId, List<SecKillApply> applyVos);
}
