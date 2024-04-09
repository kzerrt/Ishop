package com.fc.ishop.service.impl.statistics;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.statistics.MemberStatisticsData;
import com.fc.ishop.vo.StatisticsQueryParam;
import com.fc.ishop.mapper.MemberStatisticsDataMapper;
import com.fc.ishop.service.statistic.MemberStatisticsDataService;
import com.fc.ishop.util.StatisticsDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author florence
 * @date 2023/12/24
 */
@Service("memberStatisticsDataService")
public class MemberStatisticsDataServiceImpl
        extends ServiceImpl<MemberStatisticsDataMapper, MemberStatisticsData> implements MemberStatisticsDataService {
    private final static Logger logger = LoggerFactory.getLogger(MemberStatisticsDataServiceImpl.class);
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

    /**
     * 根据日期填充数据
     */
    @PostConstruct
    private void addMemberStatisticsData() {
        // 获取库中最新数据
        MemberStatisticsData recentlyOne = baseMapper.getRecentlyOne();
        Calendar todayCalender = Calendar.getInstance();
        todayCalender.set(Calendar.HOUR_OF_DAY, 0);
        todayCalender.set(Calendar.MINUTE, 0);
        todayCalender.set(Calendar.MILLISECOND, 0);
        todayCalender.set(Calendar.SECOND, 0);
        if (recentlyOne == null) {
            logger.info("数据库中没有会员新增信息，开始添加");
            Calendar clone = (Calendar) todayCalender.clone();
            clone.add(Calendar.DAY_OF_MONTH, -30);
            try {
                insertData(clone, todayCalender, new MemberStatisticsData());
            } catch (Exception e) {
                logger.error("会员活动数据添加失败！！！", e);
            }
            return;
        }
        logger.info("开始向数据库中添加最近信息.....");
        Calendar calender = Calendar.getInstance();
        calender.setTime(recentlyOne.getCreateDate());
        calender.add(Calendar.DAY_OF_MONTH, 1);
        try {
            insertData(calender, todayCalender, recentlyOne);
        } catch (Exception e) {
            logger.error("会员活动数据添加失败！！！", e);
        }
    }
    private void insertData(Calendar calendar, Calendar today,MemberStatisticsData data) throws Exception {
        if (!calendar.before(today)) {
            return;
        }
        Random random = new Random();
        int total = data.getMemberCount() == null ? 0 : data.getMemberCount();
        int num, bound = 30;
        while (calendar.before(today)) {
            num = random.nextInt(bound);
            total += num;
            data.setMemberCount(total);
            data.setNewlyAdded(num);
            data.setActiveQuantity(random.nextInt(bound));
            data.setCreateDate(calendar.getTime());
            baseMapper.insert(data);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
}
