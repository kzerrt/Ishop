package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.trade.SecKill;
import com.fc.ishop.dto.SecKillSearchParams;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.SecKillVo;

/**
 * @author florence
 * @date 2023/12/20
 */
public interface SecKillService extends IService<SecKill> {
    /**
     * 保存限时抢购
     * @param seckillVo 抢购活动信息
     */
    boolean saveSeckill(SecKillVo seckillVo);

    void modifySeckill(SecKillVo seckillVo);

    Page<SecKillVo> getSeckillByPageFromMongo(SecKillSearchParams param, PageVo pageVo);

    /**
     * 删除秒杀活动
     * @param id
     */
    void deleteSeckill(String id);

    void closeSeckill(String id);

    void openSeckill(String id);

    /**
     * 商家报名限时抢购活动
     *
     * @param storeId   商家编号
     * @param seckillId 限时抢购编号
     */
    void storeApply(String storeId, String seckillId);
}
