package com.fc.ishop.controller.statistics;

import com.fc.ishop.constant.IndexConstant;
import com.fc.ishop.enums.GoodsAuthEnum;
import com.fc.ishop.enums.GoodsStatusEnum;
import com.fc.ishop.enums.OrderStatusEnum;
import com.fc.ishop.service.GoodsService;
import com.fc.ishop.service.OrderService;
import com.fc.ishop.web.manager.statistic.IndexStatisticClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author florence
 * @date 2024/3/2
 */
@RestController
public class IndexStatisticController implements IndexStatisticClient {
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;
    @Override
    public Map<String, Object> indexStatisticsInfo() {
        Integer orderNum = orderService.orderNum(OrderStatusEnum.PAID.name());
        int goodsNum = goodsService.goodsNum(GoodsStatusEnum.UPPER.name(), GoodsAuthEnum.PASS.name());
        Integer todayUpperNum = goodsService.todayUpperNum();
        Map<String, Object> orderStatisticPrice = orderService.getOrderStatisticPrice();
        Map<String, Object> send = new HashMap<>(8);
        send.put(IndexConstant.orderNum, orderNum);
        send.put(IndexConstant.goodsNum, goodsNum);
        send.put(IndexConstant.todayGoods, todayUpperNum);
        send.put(IndexConstant.todayOrderNum, orderStatisticPrice.get("num"));
        send.put(IndexConstant.orderPrice, orderStatisticPrice.get("price"));
        return send;
    }
}
