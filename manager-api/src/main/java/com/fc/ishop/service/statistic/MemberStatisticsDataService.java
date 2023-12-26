package com.fc.ishop.service.statistic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.statistics.MemberStatisticsData;
import com.fc.ishop.vo.StatisticsQueryParam;

import java.util.List;

/**
 * 会员统计业务层
 * @author florence
 * @date 2023/12/24
 */
public interface MemberStatisticsDataService extends IService<MemberStatisticsData> {
    /**
     * 获取会员数量
     */
    Integer getMemberCount();

    /**
     * 今日新增会员数
     * @return
     */
    Integer todayMemberNum();

    /**
     * 根据参数查询时间段会员统计
     */
    List<MemberStatisticsData> statistics(StatisticsQueryParam statisticsQueryParam);
}
