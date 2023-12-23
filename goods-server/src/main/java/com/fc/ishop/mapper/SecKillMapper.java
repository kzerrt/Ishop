package com.fc.ishop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fc.ishop.dos.trade.SecKill;
import com.fc.ishop.vo.SecKillVo;

/**
 * @author florence
 * @date 2023/12/20
 */
public interface SecKillMapper extends BaseMapper<SecKill> {
    /**
     * 查询seckillvo
     * @param id
     * @return
     */
    SecKillVo selectVoById(String id);
}
