package com.fc.ishop.service.impl.statistics;

import com.fc.ishop.cache.Cache;
import com.fc.ishop.cache.CachePrefix;
import com.fc.ishop.service.statistic.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author florence
 * @date 2023/12/25
 */
@Service("platformService")
public class PlatformServiceImpl implements PlatformService {
    @Autowired
    private Cache cache;
    @Override
    public Long online() {
        Object o = cache.get(CachePrefix.ONLINE_NUM.getPrefix());
        if (o != null) {
            return (Long) o;
        }
        // 每隔10分钟统计有效token
        int size = cache.keys(CachePrefix.ACCESS_TOKEN.getPrefix() + "*").size();
        cache.put(CachePrefix.ONLINE_NUM.getPrefix(), size, 600L);
        return Long.valueOf(size);
    }
}
