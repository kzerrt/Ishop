package com.fc.ishop.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ip工具类
 * @author florence
 * @date 2023/12/3
 */
@Slf4j
public class IPUtil {
    private static final String UNKNOWN = "unknown";
    private static final Set<String> limitedIps = new HashSet<>();
    // 非法请求地址
    private static final String[] illegalUri =
            {"/**/article-category/**", "/**/article/**", "/**/promotion/current"};
    public static boolean checkUrl(String uri) {
        for (String s : illegalUri) {
            if (StringUtils.matchUri(uri, s)) {
                return true;
            }
        }
        return false;
    }
    public static boolean hasLimitedIp(String ipAddress) {
        // 检查ip地址
        checkIp(ipAddress);
        // 跳过忽略ip段

        // 从数据库中查询限制ip

        // 从缓存中查询临时限制ip

        return limitedIps.contains(ipAddress);
    }
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equals(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equals(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equals(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || UNKNOWN.equals(ip)) {
            return UNKNOWN;
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" :ip;
    }
    private static void checkIp(String ipAddress) {
        if (ipAddress == null) return;
        //log.debug("ip {} 地址错误", ipAddress);
    }
}
