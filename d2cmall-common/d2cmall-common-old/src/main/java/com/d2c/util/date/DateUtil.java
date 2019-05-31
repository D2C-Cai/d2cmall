package com.d2c.util.date;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public final static DateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
    public final static DateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
    public final static DateFormat minuteFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public final static DateFormat secondFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static DateFormat timeFormat = new SimpleDateFormat("HH:mm");
    public final static DateFormat dayFormat2 = new SimpleDateFormat("yyyy/MM/dd");
    public final static DateFormat secondFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public final static DateFormat dayFormatCn = new SimpleDateFormat("yyyy年MM月dd日");
    public final static DateFormat secondFormatCn = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

    /**
     * yyyy-MM 时间转字符串
     *
     * @param date
     * @return
     */
    public static synchronized String month2str(Date date) {
        return date == null ? null : monthFormat.format(date);
    }

    /**
     * yyyy-MM-dd 时间转字符串
     *
     * @param date
     * @return
     */
    public static synchronized String day2str(Date date) {
        return date == null ? null : dayFormat.format(date);
    }

    /**
     * yyyy/MM/dd 时间转字符串
     *
     * @param date
     * @return
     */
    public static synchronized String day2str2(Date date) {
        return date == null ? null : dayFormat2.format(date);
    }

    /**
     * yyyy-MM-dd HH:mm 时间转字符串
     *
     * @param date
     * @return
     */
    public static synchronized String minute2str(Date date) {
        return date == null ? null : minuteFormat.format(date);
    }

    /**
     * yyyy-MM-dd HH:mm:ss 时间转字符串
     *
     * @param date
     * @return
     */
    public static synchronized String second2str(Date date) {
        return date == null ? null : secondFormat.format(date);
    }

    /**
     * yyyy/MM/dd HH:mm:ss 时间转字符串
     *
     * @param date
     * @return
     */
    public static synchronized String second2str2(Date date) {
        return date == null ? null : secondFormat2.format(date);
    }

    /**
     * HH:mm 时间转字符串
     *
     * @param date
     * @return
     */
    public static synchronized String time2str(Date date) {
        return date == null ? null : timeFormat.format(date);
    }

    /**
     * yyyy年MM月dd日
     *
     * @param date
     * @return
     */
    public static synchronized String dayFormatCn(Date date) {
        return date == null ? null : dayFormatCn.format(date);
    }

    /**
     * yyyy年MM月dd日 HH:mm:ss
     *
     * @param date
     * @return
     */
    public static synchronized String secondFormatCn(Date date) {
        return date == null ? null : secondFormatCn.format(date);
    }

    /**
     * yyyy-MM 字符串转时间
     *
     * @param str
     * @return
     */
    public static synchronized Date str2month(String str) {
        try {
            return monthFormat.parse(str);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * yyyy-MM-dd 字符串转时间
     *
     * @param str
     * @return
     */
    public static synchronized Date str2day(String str) {
        try {
            return dayFormat.parse(str);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * yyyy-MM-dd HH:mm 字符串转时间
     *
     * @param str
     * @return
     */
    public static synchronized Date str2minute(String str) {
        try {
            return minuteFormat.parse(str);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * yyyy-MM-dd HH:mm:ss 字符串转时间
     *
     * @param str
     * @return
     */
    public static synchronized Date str2second(String str) {
        try {
            return secondFormat.parse(str);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * HH:mm 字符串转时间
     *
     * @param str
     * @return
     */
    public static synchronized Date str2time(String str) {
        try {
            return timeFormat.parse(str);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 其他格式字符串转
     *
     * @param str
     * @return Date
     */
    public static synchronized Date str2day2(String str) {
        try {
            return dayFormat2.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 其他格式时间转字符串
     *
     * @param date
     * @param symbol
     * @return
     */
    public static String convertDate2Str(Date date, String symbol) {
        SimpleDateFormat sf = new SimpleDateFormat(symbol);
        return sf.format(date);
    }

    /**
     * 时间加减计算
     *
     * @param date
     * @param field
     * @param amount
     * @return
     */
    public static Date add(Date date, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 指定日期的前后的interval天
     *
     * @param date
     * @param interval
     * @return
     */
    public static Date getIntervalDay(Date date, int interval) {
        if (date == null) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime((Date) date.clone());
        calendar.add(Calendar.DAY_OF_YEAR, interval);
        return calendar.getTime();
    }

    /**
     * 指定日期的前后的interval个月
     *
     * @param date
     * @param interval
     * @return
     */
    public static Date getIntervalMonth(Date date, int interval) {
        if (date == null) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime((Date) date.clone());
        Integer month = Calendar.MONTH + interval;
        if (month < 0 || month >= 12) {
            int year = new BigDecimal(month / 12).setScale(0, RoundingMode.FLOOR).intValue();
            calendar.add(Calendar.YEAR, year);
            calendar.add(Calendar.MONTH, interval - year * 12);
        } else {
            calendar.add(Calendar.MONTH, interval);
        }
        return calendar.getTime();
    }

    /**
     * 当前日期为第几年
     *
     * @param date
     * @return
     */
    public static int getYearOfDate(Date date) {
        if (date == null)
            return -1;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 当前日期为本年的第几月
     *
     * @param date
     * @return
     */
    public static int getMonthOfYear(Date date) {
        if (date == null)
            return -1;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 当前日期为本周的第几天
     *
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date) {
        if (date == null)
            return -1;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 当前日期为本月的第几天
     *
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {
        if (date == null)
            return -1;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 当前日期为本年的第几天
     *
     * @param date
     * @return
     */
    public static int getDayOfYear(Date date) {
        if (date == null)
            return -1;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 当前日期为今天的第几小时
     *
     * @param date
     * @return
     */
    public static int getHourOfDay(Date date) {
        if (date == null)
            return -1;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 日期减法 算到分钟 date1 > date2
     * <p>
     * 格式注意
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long dateSubtrationToMin(Date date1, Date date2) {
        long minute = (date1.getTime() - date2.getTime()) / (60 * 1000);
        return minute;
    }

    /**
     * 日期减法 算到小时 date2 > date1
     * <p>
     * 格式注意
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long dateSubtrationToHour(Date date1, Date date2) {
        long hour = (date1.getTime() - date2.getTime()) / (60 * 60 * 1000) > 0
                ? (date1.getTime() - date2.getTime()) / (60 * 60 * 1000)
                : (date2.getTime() - date1.getTime()) / (60 * 60 * 1000);
        return hour;
    }

    /**
     * 日期减法 算到天 date2 > date1
     * <p>
     * 格式注意
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long dateSubtrationToDay(Date date1, Date date2) {
        long day = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) > 0
                ? (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000)
                : (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    /**
     * 获取两个日期的天数差
     *
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取今日开始时间
     *
     * @param date
     * @return
     */
    public static Date getStartOfDay(Date date) {
        if (date == null)
            return date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取今日结束时间
     *
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {
        if (date == null)
            return date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取本周开始时间
     *
     * @param date
     * @return
     */
    public static Date getStartOfWeek(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
        return calendar.getTime();
    }

    /**
     * 获取本周结束时间
     *
     * @param date
     * @return
     */
    public static Date getEndOfWeek(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
        return calendar.getTime();
    }

    /**
     * 获取本月开始时间
     *
     * @param date
     * @return
     */
    public static Date getStartOfMonth(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        setMinTime(calendar);
        return calendar.getTime();
    }

    /**
     * 获取本月结束时间
     *
     * @param date
     * @return
     */
    public static Date getEndOfMonth(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(calendar);
        return calendar.getTime();
    }

    /**
     * 获取昨天的日期
     *
     * @param type
     * @return
     */
    public static Date getLastDay(Integer type) {
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.add(Calendar.DAY_OF_MONTH, -1);
        setMinTime(startCalendar);
        endCalendar.add(Calendar.DAY_OF_MONTH, -1);
        setMaxTime(endCalendar);
        if (type == 1) {
            return startCalendar.getTime();
        } else if (type == 2) {
            return endCalendar.getTime();
        } else {
            return new Date();
        }
    }

    /**
     * 获取上周的日期
     *
     * @param type
     * @return
     */
    public static Date getLastWeek(Integer type) {
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        int dayOfWeek = startCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        startCalendar.add(Calendar.DATE, offset1 - 7);
        setMinTime(startCalendar);
        endCalendar.add(Calendar.DATE, offset2 - 7);
        setMaxTime(endCalendar);
        if (type == 1) {
            return startCalendar.getTime();
        } else if (type == 2) {
            return endCalendar.getTime();
        } else {
            return new Date();
        }
    }

    /**
     * 获取上月的日期
     *
     * @param type
     * @return
     */
    public static Date getLastMonth(Integer type) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.add(Calendar.MONTH, -1);
        startCalendar.set(Calendar.DAY_OF_MONTH, startCalendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        setMinTime(startCalendar);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.add(Calendar.MONTH, -1);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);
        if (type == 1) {
            return startCalendar.getTime();
        } else if (type == 2) {
            return endCalendar.getTime();
        } else {
            return new Date();
        }
    }

    /**
     * 获取上个的季度的日期
     *
     * @param type
     * @return
     */
    public static Date getLastQuarter(Integer type) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.MONTH, (startCalendar.get(Calendar.MONTH) / 3 - 1) * 3);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        setMinTime(startCalendar);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.MONTH, (endCalendar.get(Calendar.MONTH) / 3 - 1) * 3 + 2);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);
        if (type == 1) {
            return startCalendar.getTime();
        } else if (type == 2) {
            return endCalendar.getTime();
        } else {
            return new Date();
        }
    }

    /**
     * 获取去年的日期
     *
     * @param type
     * @return
     */
    public static Date getLastYear(Integer type) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.add(Calendar.YEAR, -1);
        startCalendar.set(Calendar.MONTH, 0);
        startCalendar.set(Calendar.DAY_OF_MONTH, startCalendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        setMinTime(startCalendar);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.add(Calendar.YEAR, -1);
        endCalendar.set(Calendar.MONTH, 11);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);
        if (type == 1) {
            return startCalendar.getTime();
        } else if (type == 2) {
            return endCalendar.getTime();
        } else {
            return new Date();
        }
    }

    private static void setMinTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void setMaxTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
    }

}
