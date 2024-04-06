package com.fc.ishop.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author florence
 * @date 2024/4/5
 */
@Data
public class SeckillTimelineVo implements Serializable {
    private static final long serialVersionUID = -8171512491016990179L;

    //(value = "时刻")
    private Integer timeLine;

    //(value = "秒杀开始时间，这个是时间戳")
    private Long startTime;

   //(value = "距离本组活动开始的时间，秒为单位。如果活动的开始时间是10点，服务器时间为8点，距离开始还有多少时间")
    private Long distanceStartTime;

    //(value = "本组活动内的限时抢购商品列表")
    private List<SeckillGoodsVo> seckillGoodsList;
}
