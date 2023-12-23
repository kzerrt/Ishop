package com.fc.ishop.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * 雪花算法，分布式id获取
 * @author florence
 * @date 2023/11/28
 */
public class SnowFlake {
    /**
     * 机器id
     */
    private static long workerId = 0L;
    /**
     * 机房id
     */
    private static long datacenterId = 0L;

    private static Snowflake snowFlake = IdUtil.createSnowflake(workerId, datacenterId);

    public static long getId() {
        return snowFlake.nextId();
    }
    public static String getIdStr() {
        return snowFlake.nextIdStr();
    }

    public static String createStr(String oi) {
        return null;
    }
}
