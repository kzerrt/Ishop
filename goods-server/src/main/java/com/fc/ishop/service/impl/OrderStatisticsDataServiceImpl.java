package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.StoreFlow;
import com.fc.ishop.enums.FlowTypeEnum;
import com.fc.ishop.enums.PayStatusEnum;
import com.fc.ishop.mapper.OrderStatisticsDataMapper;
import com.fc.ishop.service.OrderService;
import com.fc.ishop.service.OrderStatisticsDataService;
import com.fc.ishop.util.CurrencyUtil;
import com.fc.ishop.util.StatisticsDateUtil;
import com.fc.ishop.utils.StringUtils;
import com.fc.ishop.vo.StatisticsQueryParam;
import com.fc.ishop.vo.order.OrderOverviewVo;
import com.fc.ishop.vo.order.OrderStatisticsDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author florence
 * @date 2023/12/26
 */
@Service("orderStatisticsDataService")
public class OrderStatisticsDataServiceImpl
        extends ServiceImpl<OrderStatisticsDataMapper, StoreFlow> implements OrderStatisticsDataService {
    @Autowired
    private OrderService orderService;
    @Override
    public OrderOverviewVo overview(StatisticsQueryParam statisticsQueryParam) {
        Date[] dates = StatisticsDateUtil.getDateArray(statisticsQueryParam);
        OrderOverviewVo vo = new OrderOverviewVo();
        //访客数
        vo.setUvNum(1);
        if (vo.getUvNum() == null) {
            vo.setUvNum(0);
        }
        //下单统计
        initOrder(dates, vo);

        //付款统计
        initPayment(dates, vo);

        //退单统计
        initAfterSale(dates, vo);

        //数据运算（转换率，比例相关）
        conversionRateOperation(vo);
        return vo;
    }

    @Override
    public List<OrderStatisticsDataVo> statisticsChart(StatisticsQueryParam statisticsQueryParam) {
        Date[] dates = StatisticsDateUtil.getDateArray(statisticsQueryParam);
        QueryWrapper queryWrapper = new QueryWrapper();
        //已支付
        queryWrapper.eq("pay_status", PayStatusEnum.PAID.name());
        //选择商家判定
        queryWrapper.eq(StringUtils.isNotEmpty(statisticsQueryParam.getStoreId()), "seller_id", statisticsQueryParam.getStoreId());
//       查询时间区间
        queryWrapper.between("create_time", dates[0], dates[1]);
//        格式化时间
        queryWrapper.groupBy("DATE_FORMAT(create_time,'%Y-%m-%d')");
        List<OrderStatisticsDataVo> orderStatisticsDataVOS = baseMapper.getOrderStatisticsData(queryWrapper);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dates[0]);

        List<OrderStatisticsDataVo> result = new ArrayList<>();
        //时间判定，将数据填充好
        //如果当前的时间，在结束时间之前
        while (calendar.getTime().before(dates[1])) {
            OrderStatisticsDataVo item = null;
            //判定是否已经有这一天的数据
            for (OrderStatisticsDataVo orderStatisticsDataVO : orderStatisticsDataVOS) {
                if (orderStatisticsDataVO.getCreateTime().equals(calendar.getTime())) {
                    item = orderStatisticsDataVO;
                }
            }
            //如果数据不存在，则进行数据填充
            if (item == null) {
                item = new OrderStatisticsDataVo();
                item.setPrice(0d);
                item.setCreateTime(calendar.getTime());
            }
            result.add(item);
            //增加时间
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }
        return result;
    }

    private void conversionRateOperation(OrderOverviewVo orderOverviewVO) {
            //下单转换率 订单数/UV
        double orderConversionRate = CurrencyUtil.div(orderOverviewVO.getOrderNum(), orderOverviewVO.getUvNum(), 4);
        if (orderConversionRate > 1) {
            orderConversionRate = 1d;
        }
        orderOverviewVO.setOrderConversionRate(CurrencyUtil.mul(orderConversionRate, 100) + "%");
        //付款转换率 付款订单数/订单数
        double paymentsConversionRate = CurrencyUtil.div(orderOverviewVO.getPaymentOrderNum(), orderOverviewVO.getOrderNum(), 4);
        if (paymentsConversionRate > 1) {
            paymentsConversionRate = 1d;
        }
        orderOverviewVO.setPaymentsConversionRate(CurrencyUtil.mul(paymentsConversionRate, 100) + "%");
        //整体转换率 付款数/UV
        double overallConversionRate = CurrencyUtil.div(orderOverviewVO.getPaymentOrderNum(), orderOverviewVO.getUvNum(), 4);
        if (overallConversionRate > 1) {
            overallConversionRate = 1d;
        }
        orderOverviewVO.setOverallConversionRate(CurrencyUtil.mul(overallConversionRate, 100) + "%");
    }

    private void initAfterSale(Date[] dates, OrderOverviewVo vo) {
        //付款订单数，付款金额
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.between("create_time", dates[0], dates[1]);
        queryWrapper.select("SUM(final_price) AS price , COUNT(0) AS num");
        queryWrapper.eq("flow_type", FlowTypeEnum.REFUND.name());
        Map payment = this.getMap(queryWrapper);
        vo.setRefundOrderNum(payment != null && payment.containsKey("num") ? (Long) payment.get("num") : 0L);
        vo.setRefundOrderPrice(payment != null && payment.containsKey("price") ? (Double) payment.get("price") : 0D);
    }

    private void initPayment(Date[] dates, OrderOverviewVo vo) {
        //付款订单数，付款金额
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.between("create_time", dates[0], dates[1]);
        queryWrapper.select("SUM(final_price) AS price , COUNT(0) AS num");
        queryWrapper.eq("flow_type", FlowTypeEnum.PAY.name());
        Map payment = this.getMap(queryWrapper);

        vo.setPaymentOrderNum(payment != null && payment.containsKey("num") ? (Long) payment.get("num") : 0L);
        vo.setPaymentAmount(payment != null && payment.containsKey("price") ? (Double) payment.get("price") : 0D);

        //付款人数
        queryWrapper = Wrappers.query();
        queryWrapper.between("create_time", dates[0], dates[1]);
        queryWrapper.select("COUNT(0) AS num");
        queryWrapper.groupBy("member_id");
        Map paymentMemberNum = this.getMap(queryWrapper);

        vo.setPaymentsNum(paymentMemberNum != null && paymentMemberNum.containsKey("num") ? (Long) paymentMemberNum.get("num") : 0L);
    }

    private void initOrder(Date[] dates, OrderOverviewVo vo) {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.between("create_time", dates[0], dates[1]);
        queryWrapper.select("SUM(flow_price) AS price , COUNT(0) AS num");
        Map order = orderService.getMap(queryWrapper);
        if (order == null) {
            vo.setOrderNum(0L);
            vo.setOrderAmount((double) 0L);
        } else {
            vo.setOrderNum(order.containsKey("num") ? (Long) order.get("num") : 0L);
            vo.setOrderAmount(order.containsKey("price") ? (double) order.get("price") : 0L);
        }


        queryWrapper = Wrappers.query();
        queryWrapper.between("create_time", dates[0], dates[1]);
        queryWrapper.select("count(DISTINCT member_id) AS num");
        Map memberNum = orderService.getMap(queryWrapper);

        vo.setOrderMemberNum(memberNum != null && memberNum.containsKey("num") ? (Long) memberNum.get("num") : 0L);

        if (vo.getOrderAmount() == null) {
            vo.setOrderAmount(0D);
        }
    }
}
