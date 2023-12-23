package com.fc.ishop.utils;

import cn.hutool.core.util.StrUtil;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtils extends StrUtil{

    public static String md5(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] resultByte = messageDigest.digest(str.getBytes(StandardCharsets.UTF_8));
        StringBuffer result = new StringBuffer();
        for (byte b : resultByte) {
            int v = 0xFF & b;
            if (v < 16) {
                result.append("0");
            }
            result.append(Integer.toHexString(v));
        }
        return result.toString();
    }

    /**
     * 驼峰法转下划线
     */
    public static String camel2Underline(String str) {

        if (StrUtil.isBlank(str)) {
            return "";
        }
        if (str.length() == 1) {
            return str.toLowerCase();
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                sb.append("_" + Character.toLowerCase(str.charAt(i)));
            } else {
                sb.append(str.charAt(i));
            }
        }
        return (str.charAt(0) + sb.toString()).toLowerCase();
    }

    /**
     * 字符拼接成相对路径
     * 只拼接路径，不拼接文件
     */
    public static String getRelativePath(String... paths) {
        if (paths == null || paths.length == 0) return "";
        StringBuilder str = new StringBuilder();
        for (String path : paths) {
            str.append(path).append('/');
        }
        /*str.append(paths[len]);
        if (!paths[len].endsWith(".")) {
            str.append('/');
        }*/
        return checkPath(str.toString());
    }
    /**
     * 检查路径是否正确，不对则返回正确路径
     */
    public static String checkPath(String path) {
        boolean preChr = false;// 前缀是否含有/
        int len = path.length();
        StringBuilder str = new StringBuilder();
        char c;
        for (int i = 0; i < len; i++) {
            c = path.charAt(i);
            if (c == ' ') {
                continue;
            }
            if (c == '/') {
                if (!preChr) {
                    preChr = true;
                    str.append(c);
                }
            } else {
                if (preChr) {
                    preChr = false;
                }
                str.append(c);
            }
        }
        return str.toString();
    }

    /**
     *匹配请求路径，支持通配符
     * 两种方式，一种通过切割字符串
     * 另一种遍历整个字符串（较复杂）
     * @return
     */
    public static boolean matchUri(String uri, List<String> ignoreUrls) {
        // 使用位运算减少空间复杂度
        if (uri == null || ignoreUrls == null || ignoreUrls.size() == 0) return false;
        int i = uri.indexOf("?");
        if (i >= 0) {
            uri = uri.substring(0, i);
        }
        List<String> paths = Arrays.stream(uri.split("/")).filter(item -> !"".equals(item)).collect(Collectors.toList());
        if (paths.size() > 31) {
            throw new IllegalArgumentException("uri is too long");
        }
        for (String ignoreUrl : ignoreUrls) {
            if (matcherUri(paths, ignoreUrl)) {
                return true;
            }
        }

        // 不存在一个模式能够匹配上
        return false;
    }
    public static boolean matchUri(String uri, String pattern) {
        int i = uri.indexOf("?");
        if (i > 0) {
            uri = uri.substring(0, i);
        }
        List<String> paths = Arrays.stream(uri.split("/")).filter(item -> !"".equals(item)).collect(Collectors.toList());
        return matcherUri(paths,pattern);
    }
    private static boolean matcherUri(List<String> paths, String pattern) {
        int pathSize = paths.size();
        List<String> patterns = Arrays.stream(pattern.split("/")).filter(item -> !"".equals(item)).collect(Collectors.toList());
        // * || ** 路径可以为空
        if (patterns.size() - 1 > pathSize) {
            return false;
        }

        int result, pre = 1;// 第一位边界判断,判断结果范围为[0, pathSize + 1]
        boolean isDouStar;
        boolean hasStar;// 匹配串中是否含有**
        for (String pat : patterns) {// pattern 边界[0,patternSize)
            isDouStar = "**".equals(pat);
            hasStar = pat.contains("**");
            result = 0;
            for (int j = 0; j < pathSize; j++) {// uri 边界[0,pathSize)
                if ((pre & (1 << j)) == 0) {// 判断pre[j-1] 是否匹配成功
                    continue;// 匹配失败跳过
                }

                if (isDouStar) { // 后续全部为true
                    // 能进来这里，一是第一p[-1]匹配成功的位置
                    result = ((1 << (pathSize + 1)) - 1) ^ ((1 << (j + 1)) - 1);// 将[j, m)后位数变为1
                    break;
                } else if (hasStar) { // **.html
                    if (paths.get(j).matches(pat.replace(".", "\\.").replace("**", ".+"))) {
                        result |= (1 << (j + 1));
                    }
                } else {// 普通字符串或*
                    if ("*".equals(pat) || paths.get(j).equals(pat)) {
                        result |= (1 << (j + 1));
                    }
                }
            }
            pre = result;
        }

        return (pre & (1 << pathSize)) != 0;
    }

    /**
     * 将字母变成大写
     * @param name
     * @return
     */
    public static String upperCase(String name) {
        if (name == null || name.length() == 0) return null;
        StringBuilder si = new StringBuilder();
        int len = name.length();
        int cot = 'a' - 'A';
        char c;
        for (int i = 0; i < len; i++) {
            c = name.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                si.append((char) (c + cot));
            } else {
                si.append(c);
            }
        }
        return si.toString();
    }
    public static String joinStr(String... arrays) {
        return join(arrays, (String) null);
    }
    public static String join(String[] arrays, String separator) {
        return arrays == null ? null : join(arrays, separator, 0, arrays.length);
    }

    private static String join(String[] arrays, String separator, int startIndex, int endIndex) {
        if (arrays == null) {
            return null;
        } else {
            if (separator == null) {
                separator = "";
            }
            // 计算数组元素数量
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder si = new StringBuilder(noOfItems);
                if (arrays[startIndex] != null) {
                    si.append(arrays[startIndex]);
                }
                for (int i = startIndex + 1; i < endIndex; i++) {
                    si.append(separator).append(arrays[i]);
                }
                return si.toString();
            }
        }
    }

    public static String getRandStr(int i) {
        return null;
    }
    /*public static boolean matchUri(String url, List<String> ignoreUrls) {
        if (url == null || ignoreUrls == null || ignoreUrls.size() == 0) return false;
        String[] paths = url.split("/");
        int pathSize = paths.length;
        if (pathSize > 31) {
            throw new IllegalArgumentException("uri is too long");
        }
        int patternSize;
        for (String ignoreUrl : ignoreUrls) {
            String[] patterns = ignoreUrl.split("/");
            patternSize = patterns.length;
            // * || ** 路径可以为空
            if (patterns.length - 1 > paths.length) {
                continue;
            }
            boolean[] flag = new boolean[pathSize];
            boolean isDouStar;
            for (int i = 0; i < patternSize; i++) {
                isDouStar = "**".equals(patterns[i]);
                for (int j = 0; j < pathSize; j++) {
                    if (isDouStar) { // 后续全部为true
                       flag[j] = true;
                    }

                    if (flag[j]) continue;

                    if ("*".equals(patterns[i])) {
                        flag[j] = true;
                        break;
                    } else {
                        if (patterns[i].equals(paths[j])) {
                            flag[j] = true;
                            break;
                        }
                    }
                }
                // 如果第i个位置没有匹配到，跳出整个循环
                if (!flag[i]) {
                    break;
                }
            }
            // 最后一个特殊判断
            if (flag[pathSize - 2] && matchSingleton(paths[pathSize - 1], patterns[patternSize - 1])) {
                return true;
            }
        }

        // 不存在一个模式能够匹配上
        return false;
    }*/

}
