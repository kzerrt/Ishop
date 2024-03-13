package com.fc.ishop.controller.statistics;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fc.ishop.dos.goods.Goods;
import com.fc.ishop.dto.GoodsStatisticsQueryParam;
import com.fc.ishop.enums.GoodsStatusEnum;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.enums.UserEnums;
import com.fc.ishop.security.context.UserContext;
import com.fc.ishop.service.GoodsService;
import com.fc.ishop.service.GoodsStatisticsDataService;
import com.fc.ishop.utils.StringUtils;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.goods.GoodsStatisticsDataVo;
import com.fc.ishop.web.manager.statistic.GoodsStatisticClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品统计
 * @author florence
 * @date 2023/12/26
 */
@RestController
public class GoodsStatisticsController implements GoodsStatisticClient {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsStatisticsDataService goodsStatisticsData;



    @Override
    public ResultMessage<List<GoodsStatisticsDataVo>> getByPage(GoodsStatisticsQueryParam goodsStatisticsQueryParam) {
        return ResultUtil.data(goodsStatisticsData.getGoodsStatisticsData(goodsStatisticsQueryParam, 100));
    }
}
