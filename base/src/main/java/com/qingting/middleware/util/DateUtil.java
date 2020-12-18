package com.qingting.middleware.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 日期工具类
 */
@Slf4j
public class DateUtil {
    public static final String FORMAT_SIMPLE_DASH = "yyyy-MM-dd";
    public static final String FORMAT_DASH_NO_SECOND = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_FULL_DASH = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_NUMBER_NO_SECOND = "yyyyMMddHHmm";
    public static final String FORMAT_FULL_NUMBER = "yyyyMMddHHmmss";
    public static final String FORMAT_SIMPLE_NUMBER = "yyyyMMdd";
    public static final String FORMAT_FULL_SLASH = "yyyy/MM/dd  HH/mm/ss";
    public static final String FORMAT_SIMPLE_SLASH = "yyyy/MM/dd";
    public static final String FORMAT_FULL_CHINESE = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String FORMAT_SIMPLE_CHINESE = "yyyy年MM月dd日";


    /**
     * Date类型转换为String类型
     *
     * @param date   日期对象
     * @param format 转换格式
     * @return 日期字符串
     */
    public static String format(Date date, String format) {
        try {
            return new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
            log.error("日期转换错误:" + format);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * String类型转换为Date类型
     *
     * @param strDate 日期字符串
     * @param format  转换格式
     * @return 日期对象
     */
    public static Date format(String strDate, String format) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            log.error("日期格式错误: " + strDate + "转换成" + format + "类型的日期");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期字符串格式转换
     *
     * @param str         原日期字符串
     * @param from_format 原日期字符串格式
     * @param to_format   需要转化的格式
     * @return 转化后的格式
     */
    public static String format(String str, String from_format, String to_format) {
        SimpleDateFormat from = new SimpleDateFormat(from_format);
        SimpleDateFormat tod = new SimpleDateFormat(to_format);
        try {
            Date retStrFormatNowDate = from.parse(str);
            return tod.format(retStrFormatNowDate);
        } catch (ParseException e) {
            log.error("日期转换错误：" + str + "由" + from_format + "格式转换成" + to_format);
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取当前时间戳
     *
     * @return 时间戳
     */
    public static Long getNowTimestamp() {
        return (System.currentTimeMillis() / 1000);
    }

    /**
     * 时间戳转换成日期对象
     *
     * @param time 时间戳
     * @return 日期对象
     */
    public static Date unix2Date(Long time) {
        Long timestamp = time * 1000;
        return new Date(timestamp);
    }

    /**
     * 日期转换成时间戳
     *
     * @param date 日期对象
     * @return 时间戳
     */
    public static Long getDateTimestamp(Date date) {
        return date.getTime() / 1000;
    }

    /**
     * 时间字符串转成时间戳
     *
     * @param strDate 日期字符串
     * @param format  转换格式
     * @return
     */
    public static Long date2Timestamp(String strDate, String format) {
        Date date;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            date = simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            log.error("日期转换错误：" + strDate + "由" + format + "格式转换成时间戳");
            e.printStackTrace();
            return null;
        }
        return date.getTime() / 1000;
    }


    /**
     * 时间戳转换成日期字符串
     *
     * @param timestamp 时间戳
     * @param format    格式
     * @return 日期字符串
     */
    public static String fromUnixTime(Long timestamp, String format) {
        Long temp = timestamp * 1000;
        try {
            return new SimpleDateFormat(format).format(new Date(temp));
        } catch (Exception e) {
            log.error("日期转换错误：" + timestamp + "由时间戳转换成" + format + "格式");
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 得到传入日期n天后的日期,如果传入日期为null，则表示当前日期n天后的日期
     *
     * @param date 操作日期
     * @param days 可以为任何整数，负数表示前days天，正数表示后days天
     * @return Date 日期对象
     */
    public static Date getAddDayDate(Date date, int days) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + days);
        return calendar.getTime();
    }

    /**
     * 得到传入日期n分钟后的日期,如果传入日期为null，则表示当前日期n分钟后的日期
     *
     * @param date 操作日期
     * @param ms   可以为任何整数，负数表示前mins分钟，正数表示后mins分钟
     * @return Date 日期对象
     */
    public static Date addMinute(Date date, int ms) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, ms);
        return calendar.getTime();
    }

    /**
     * 得到传入日期n年后的日期,如果传入日期为null，则表示当前日期n年后的日期
     *
     * @param date  操作日期
     * @param years 可以为任何整数，负数表示前years年，正数表示后years年
     * @return Date 日期对象
     */
    public static Date addYear(Date date, Integer years) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }

    /**
     * 得到传入日期n月后的日期,如果传入日期为null，则表示当前日期n月后的日期
     *
     * @param date   操作日期
     * @param months 可以为任何整数，负数表示前months月，正数表示后months月
     * @return Date 日期对象
     */
    public static Date addMonth(Date date, Integer months) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }


    /**
     * 提取日期字符串中的数字
     *
     * @param str 日期字符串
     * @return 日期字符串
     */
    public static String dateToNumberStr(String str) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }


    /**
     * 一段时间内获取每一天的数组
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 日期字符串数组
     */
    public static List<String> collectLocalDateDays(LocalDate startDate, LocalDate endDate) {
        // 用起始时间作为流的源头，按照每次加一天的方式创建一个无限流
        return Stream.iterate(startDate, localDate -> localDate.plusDays(1))
                // 截断无限流，长度为起始时间和结束时间的差+1个
                .limit(ChronoUnit.DAYS.between(startDate, endDate) + 1)
                // 由于最后要的是字符串，所以map转换一下
                .map(LocalDate::toString)
                // 把流收集为List
                .collect(Collectors.toList());
    }

    /**
     * 一段时间内获取每一月的数组
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 日期字符串数组
     */
    public static List<String> collectLocalDateMonths(LocalDate startDate, LocalDate endDate) {
        // 用起始时间作为流的源头，按照每次加一天的方式创建一个无限流
        return Stream.iterate(startDate, localDate -> localDate.plusMonths(1))
                // 截断无限流，长度为起始时间和结束时间的差+1个
                .limit(ChronoUnit.MONTHS.between(startDate, endDate) + 1)
                // 由于最后要的是字符串，所以map转换一下
                .map(LocalDate::toString)
                // 把流收集为List
                .collect(Collectors.toList());
    }

    /**
     * 获取2个日期的相隔天数
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 天数
     */
    public static Integer differentDay(Date startDate, Date endDate) {
        return (int) ((startDate.getTime() - endDate.getTime()) / (3600 * 1000 * 24));
    }

    /**
     * 获取2个日期的相隔秒数
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 秒数
     */
    public static Integer differentSecond(Date startDate, Date endDate) {
        return (int) (startDate.getTime() - endDate.getTime()) / 1000;
    }


}
