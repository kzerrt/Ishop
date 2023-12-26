package com.fc.ishop.service.statistic;

import com.fc.ishop.vo.OnlineMemberVo;

import java.util.List;

/**
 * 统计用户在线信息
 * @author florence
 * @date 2023/12/25
 */
public interface PlatformService {

    /**
     * 统计在线人数
     */
    Long online();

    List<OnlineMemberVo> statisticsOnline();

}
