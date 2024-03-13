package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.StoreFlow;
import com.fc.ishop.dto.GoodsStatisticsQueryParam;
import com.fc.ishop.mapper.GoodsStatisticsDataMapper;
import com.fc.ishop.service.GoodsStatisticsDataService;
import com.fc.ishop.util.StatisticsDateUtil;
import com.fc.ishop.utils.StringUtils;
import com.fc.ishop.vo.goods.GoodsStatisticsDataVo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/26
 */
@Service("goodsStatisticsDataService")
public class GoodsStatisticsDataServiceImpl
        extends ServiceImpl<GoodsStatisticsDataMapper, StoreFlow> implements GoodsStatisticsDataService {
    @Override
    public List<GoodsStatisticsDataVo> getGoodsStatisticsData(GoodsStatisticsQueryParam goodsStatisticsQueryParam, int num) {
        //获取查询条件
        QueryWrapper queryWrapper = getQueryWrapper(goodsStatisticsQueryParam);
        //根据商品分组
        queryWrapper.groupBy("goods_id");
        queryWrapper.groupBy("goods_name");

        queryWrapper.eq(!StringUtils.isEmpty(goodsStatisticsQueryParam.getStoreId()), "store_id", goodsStatisticsQueryParam.getStoreId());
        //查询前一百条记录
        Page page = new Page<GoodsStatisticsDataVo>(1, num);
        return baseMapper.getGoodsStatisticsData(page, queryWrapper);
    }
    private QueryWrapper getQueryWrapper(GoodsStatisticsQueryParam goodsStatisticsQueryParam) {

        QueryWrapper queryWrapper = Wrappers.query();
        //判断搜索类型是：年、月
        Date[] date = StatisticsDateUtil.getDateArray(goodsStatisticsQueryParam);
        queryWrapper.between("create_time", date[0], date[1]);

        //判断是按照数量统计还是按照金额统计
        if (goodsStatisticsQueryParam.getType().equals("NUM")) {
            queryWrapper.orderByDesc("num");
        } else {
            queryWrapper.orderByDesc("price");
        }
        //设置为付款查询
        queryWrapper.eq("flow_type", "PAY");
        return queryWrapper;
    }
}
