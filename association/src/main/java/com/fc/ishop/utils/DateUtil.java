package com.fc.ishop.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd";

    public static final String STANDARD_DATE_NO_UNDERLINE_FORMAT = "yyyyMMdd";

    public static final String FULL_DATE = "yyyyMMddHHmmss";

    /**
     * 将一个字符串转换成日期格式
     *
     * @param date    字符串日期
     * @param pattern 日期格式
     * @return
     */
    public static Date toDate(String date, String pattern) {
        if ("".equals("" + date)) {
            return null;
        }
        if (pattern == null) {
            pattern = STANDARD_DATE_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        Date newDate = new Date();
        try {
            newDate = sdf.parse(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return newDate;
    }
}
