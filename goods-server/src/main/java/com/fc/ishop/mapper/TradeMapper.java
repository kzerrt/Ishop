package com.fc.ishop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fc.ishop.dos.trade.Trade;
import org.apache.ibatis.annotations.Update;

/**
 * @author florence
 * @date 2023/12/18
 */
public interface TradeMapper extends BaseMapper<Trade> {

    @Update("UPDATE li_trade SET flow_price =(SELECT SUM(flow_price) FROM i_order WHERE trade_sn=#{tradeSn}) WHERE sn=#{tradeSn}")
    void updateTradePrice(String tradeSn);
}
