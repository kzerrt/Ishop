package com.fc.ishop.service.impl.statistics;

import com.fc.ishop.cache.Cache;
import com.fc.ishop.cache.CachePrefix;
import com.fc.ishop.service.statistic.PlatformService;
import com.fc.ishop.vo.OnlineMemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/25
 */
@Service("platformService")
public class PlatformServiceImpl implements PlatformService {
    @Autowired
    @Qualifier("redisObj")
    private Cache cache;
    @Override
    public Long online() {
        Object o = cache.get(CachePrefix.ONLINE_NUM.getPrefix());
        if (o != null) {
            if (o instanceof Integer) {
                return ((Integer) o).longValue();
            } else {
                return 1L;
            }
        }
        // 每隔10分钟统计有效token
        List keys = cache.keys(CachePrefix.ACCESS_TOKEN.getPrefix() + "*");
        int size;
        if (keys == null) {
            size = 1;
        } else {
            size = keys.size() + 1;
        }

        cache.put(CachePrefix.ONLINE_NUM.getPrefix(), size, 600L);
        return (long) size;
    }

    @Override
    public List<OnlineMemberVo> statisticsOnline() {
        /*Object object = cache.get(CachePrefix.ONLINE_MEMBER.getPrefix());
        List<OnlineMemberVo> result = new ArrayList<>();
        if (object != null) {
            result = (List<OnlineMemberVo>) object;
        }
        return this.initData(result);*/
        return null;
    }

    /**
     * 在线人数初始化
     */
    private List<OnlineMemberVo> initData(List<OnlineMemberVo> source) {

        return null;
    }
}
