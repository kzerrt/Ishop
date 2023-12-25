package com.fc.ishop.controller.statistics;

import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.service.statistic.IndexStatisticsService;
import com.fc.ishop.vo.IndexStatisticsVo;
import com.fc.ishop.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理端首页 信息统计
 * @author florence
 * @date 2023/12/24
 */
@RestController
@RequestMapping("/statistics/index")
public class IndexStatisticsManagerController {
    /**
     * 首页统计
     */
    @Autowired
    private IndexStatisticsService indexStatisticsService;

    /**
     * 获取首页查询数据
     */
    @GetMapping
    public ResultMessage<IndexStatisticsVo> index() {
        return ResultUtil.data(indexStatisticsService.indexStatistics());
    }

}
