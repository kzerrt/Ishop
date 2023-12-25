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

    @Select("SELECT  COUNT(0)  FROM li_member  ${ew.customSqlSegment}")
    Integer customSqlQuery(@Param(Constants.WRAPPER) QueryWrapper queryWrapper);
}
