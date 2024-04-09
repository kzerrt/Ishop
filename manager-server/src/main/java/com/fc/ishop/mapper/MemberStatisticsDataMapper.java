package com.fc.ishop.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.fc.ishop.dos.statistics.MemberStatisticsData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author florence
 * @date 2023/12/24
 */
public interface MemberStatisticsDataMapper extends BaseMapper<MemberStatisticsData> {

    @Select("SELECT  COUNT(id)  FROM i_member  ${ew.customSqlSegment}")
    Integer customSqlQuery(@Param(Constants.WRAPPER) QueryWrapper queryWrapper);
    @Select("SELECT id, active_quantity AS activeQuantity, create_date AS createDate,member_count AS memberCount,newly_added AS newlyAdded\n" +
            "FROM `i_member_statistics_data` a, \n" +
            "(SELECT MAX(create_date) date FROM `i_member_statistics_data`) b WHERE b.date = a.create_date;")
    MemberStatisticsData getRecentlyOne();
}
