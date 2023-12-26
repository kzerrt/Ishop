package com.fc.ishop.controller.statistics;

import com.fc.ishop.dos.statistics.MemberStatisticsData;
import com.fc.ishop.vo.StatisticsQueryParam;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.service.statistic.MemberStatisticsDataService;
import com.fc.ishop.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/25
 */
@RestController
@RequestMapping("/statistics/member")
public class MemberStatisticsManagerController {
    @Autowired
    private MemberStatisticsDataService memberStatisticsDataService;

    @GetMapping
    public ResultMessage<List<MemberStatisticsData>> getByList(StatisticsQueryParam statisticsQueryParam) {
        return ResultUtil.data(memberStatisticsDataService.statistics(statisticsQueryParam));
    }
}
