package org.example.demo.api.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

    public static String DEFAULT_PATTERN = "yyyy-MM-dd";
    public static String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static String TIME_PATTERN = "HH:mm:ss";
    public static String YEAR = "y";
    public static String MONTH = "m";
    public static String DAY = "d";
    public static String HOUR = "h";
    public static String MINUTE = "mi";
    public static String SECOND = "s";

    /**
     * 格式化日期 默认年月日
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        LocalDate localDate = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_PATTERN);
        return localDate.format(formatter);
    }

    /**
     * 指定格式 格式化日期
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }

    /**
     * 字符串转日期
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date parseDate(String date, String pattern) {
        LocalDateTime localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern));
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取日期中的指定值
     *
     * @param date
     * @param type
     * @return
     */
    public static int getValue(Date date, String type) {
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        switch (type) {
            case "y":
                return localDateTime.getYear();
            case "m":
                return localDateTime.getMonthValue();
            case "d":
                return localDateTime.getDayOfMonth();
            case "h":
                return localDateTime.getHour();
            case "mi":
                return localDateTime.getMinute();
            case "s":
                return localDateTime.getSecond();
            default:
                return -1;
        }
    }
}
