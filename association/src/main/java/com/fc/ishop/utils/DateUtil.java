package com.fc.ishop.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        if ("".equals(date)) {
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
    public static Date toDate(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        if (pattern == null) {
            pattern = STANDARD_DATE_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        String format = sdf.format(date);
        Date newDate = null;
        try {
            newDate = sdf.parse(format);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return newDate;
    }

    public static long getDateline() {
        return System.currentTimeMillis() / 1000;
    }
    /**
     * 获取延时时间（秒）
     *
     * @param startTime 开始时间
     * @return 延时时间（秒）
     */
    public static int getDelayTime(long startTime) {
        int time = Math.toIntExact((startTime - System.currentTimeMillis()) / 1000);
        // 如果时间为负数则改为一秒后执行
        if (time <= 0) {
            time = 1;
        }
        return time;
    }

    /**
     * 把日期转换成字符串型
     *
     * @param date    日期
     * @param pattern 类型
     * @return
     */
    public static String toString(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        if (pattern == null) {
            pattern = STANDARD_DATE_FORMAT;
        }
        String dateString = "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            dateString = sdf.format(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dateString;
    }
    /**
     * 时间戳转换成时间类型
     *
     * @param time    时间戳
     * @param pattern 格式
     * @return
     */
    public static String toString(Long time, String pattern) {
        if (time > 0) {
            if (time.toString().length() == 10) {
                time = time * 1000;
            }
            Date date = new Date(time);
            String str = DateUtil.toString(date, pattern);
            return str;
        }
        return "";
    }
    /**
     * 当天的结束时间
     *
     * @return
     */
    public static Date endOfDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date endOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static long startOfTodDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        return date.getTime() / 1000;
    }

    public static long getDateline(String date, String pattern) {
        return toDate(date, pattern).getTime() / 1000;
    }
}
