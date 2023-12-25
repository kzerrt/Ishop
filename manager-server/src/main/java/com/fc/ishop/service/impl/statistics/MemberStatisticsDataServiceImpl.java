package com.fc.ishop.service.impl.statistics;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.statistics.MemberStatisticsData;
import com.fc.ishop.dto.StatisticsQueryParam;
import com.fc.ishop.mapper.MemberStatisticsDataMapper;
import com.fc.ishop.service.statistic.MemberStatisticsDataService;
import com.fc.ishop.util.StatisticsDateUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/24
 */
@Service("memberStatisticsDataService")
public class MemberStatisticsDataServiceImpl
        extends ServiceImpl<MemberStatisticsDataMapper, MemberStatisticsData> implements MemberStatisticsDataService {
    @Override
    public Integer getMemberCount() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("disabled", true);
        return baseMapper.customSqlQuery(queryWrapper);
    }

    @Override
    public Integer todayMemberNum() {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.gt("create_time", DateUtil.beginOfDay(new Date()));
        return baseMapper.customSqlQuery(queryWrapper);
    }

    @Override
    public List<MemberStatisticsData> statistics(StatisticsQueryParam statisticsQueryParam) {
        Date[] dates = StatisticsDateUtil.getDateArray(statisticsQueryParam);
        Date startTime = dates[0], endTime = dates[1];
        QueryWrapper<MemberStatisticsData> queryWrapper = Wrappers.query();
        queryWrapper.between("create_date", startTime, endTime);
        return list(queryWrapper);
    }
}
