package com.d2c.common.base.utils;

import com.d2c.common.base.enums.DateType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类
 *
 * @author wull
 */
public class DateUt {

    public final static long SECOND_MILLIS = 1000;
    public final static long MINUTE_MILLIS = 60 * 1000;
    public final static long HOUR_MILLIS = 60 * 60 * 1000;
    // *********************** FastDateFormat ************************
    public final static FastDateFormat monthFormat = FastDateFormat.getInstance("yyyy-MM");
    public final static FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy-MM-dd");
    public final static FastDateFormat minuteFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm");
    public final static FastDateFormat secondFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    public final static FastDateFormat timeFormat = FastDateFormat.getInstance("HH:mm");

    public static long getTimeInMillis() {
        return System.currentTimeMillis();
    }

    public static long getTimeInMillis(String time) {
        return ConvertUt.convertType(time, Long.class);
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * 今天日期: yyyy-MM-dd
     */
    public static String today() {
        return DateUt.date2str(Calendar.getInstance().getTime());
    }

    /**
     * 昨天日期: yyyy-MM-dd
     */
    public static String yesterday() {
        return rollDayStr(-1);
    }

    /**
     * N天日期: yyyy-MM-dd
     */
    public static String rollDayStr(int day) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, day);
        return DateUt.date2str(cal.getTime());
    }

    /**
     * 获得日期一年中的第几天
     */
    public static int toDayOfYear(Date date) {
        return getCalendar(date).get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 秒
     */
    public static long toSecond(Date date) {
        return date.getTime() / 1000;
    }

    /**
     * 获得今年最大天数
     */
    public static int getMaxDayOfYear() {
        return Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    /**
     * 今天的日期
     */
    public static Date getCurrentDate() {
        return getCurrentDateTime(0, 0, 0).getTime();
    }

    /**
     * 今天的几点几时几分
     */
    public static Calendar getCurrentDateTime(Integer hour, Integer minute, Integer second) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    /**
     * 今天的几点几时几分
     */
    public static Calendar getCalendarTime(Date date, Integer hour, Integer minute, Integer second) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    /**
     * 时间根据类型整数回滚
     * <p>DAY num = 0 为今天， num = 30 为30天内
     *
     * @param dateType HOUR, DAY, WEEK, MONTH, YEAR
     * @see DateType
     */
    public static Date dateBack(DateType dateType, Integer num) {
        return dateBack(null, dateType, num);
    }

    public static Date dateBack(Date date, DateType dateType, Integer num) {
        if (num == null) {
            num = 0;
        }
        return dateAdd(date, dateType, -num);
    }

    public static Date dateAdd(DateType dateType, Integer num) {
        return dateAdd(null, dateType, num);
    }

    public static Date dateAdd(Date date, DateType dateType, Integer num) {
        if (num == null) {
            num = 0;
        }
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        switch (dateType) {
            case HOUR:
                cal.setTimeInMillis(cal.getTimeInMillis() + (num * HOUR_MILLIS));
                break;
            case DAY:
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.add(Calendar.DAY_OF_YEAR, num);
                break;
            case WEEK:
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.add(Calendar.WEEK_OF_YEAR, num);
                break;
            case MONTH:
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.add(Calendar.MONTH, num);
                break;
            case YEAR:
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.add(Calendar.YEAR, num);
                break;
            default:
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.add(Calendar.DAY_OF_YEAR, num);
                break;
        }
        return cal.getTime();
    }

    public static Date dateRollBack(Date date, DateType dateType, Integer num) {
        if (num == null || num == 0) {
            return date;
        }
        return dateRoll(date, dateType, -num);
    }

    public static Date dateRoll(Date date, DateType dateType, Integer num) {
        if (num == null || num == 0) {
            return date;
        }
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        switch (dateType) {
            case HOUR:
                cal.roll(Calendar.HOUR_OF_DAY, num);
                break;
            case DAY:
                cal.roll(Calendar.DAY_OF_YEAR, num);
                break;
            case WEEK:
                cal.roll(Calendar.WEEK_OF_YEAR, num);
                break;
            case MONTH:
                cal.roll(Calendar.MONTH, num);
                break;
            case YEAR:
                cal.roll(Calendar.YEAR, num);
                break;
            default:
                break;
        }
        return cal.getTime();
    }

    /**
     * 几天前/后的日期
     */
    public static Date dayAdd(Integer days) {
        return dateAdd(DateType.DAY, days);
    }

    public static Date dayAdd(Date date, Integer days) {
        return dateAdd(date, DateType.DAY, days);
    }

    public static Date dayBack(Integer days) {
        return dateBack(DateType.DAY, days);
    }

    public static Date dayBack(Date date, Integer days) {
        return dateBack(date, DateType.DAY, days);
    }

    public static Date dayRoll(Integer days) {
        return dayRoll(new Date(), days);
    }

    public static Date dayRoll(Date date, Integer days) {
        return dateRoll(date, DateType.DAY, days);
    }

    public static Date dayRollBack(Integer days) {
        return dayRollBack(new Date(), days);
    }

    public static Date dayRollBack(Date date, Integer days) {
        return dateRollBack(date, DateType.DAY, days);
    }

    /**
     * 几月前/后的日期
     */
    public static Date monthAdd(Integer num) {
        return dateAdd(DateType.MONTH, num);
    }

    public static Date monthAdd(Date date, Integer num) {
        return dateAdd(date, DateType.MONTH, num);
    }

    public static Date monthBack(Integer num) {
        return dateBack(DateType.MONTH, num);
    }

    public static Date monthBack(Date date, Integer num) {
        return dateBack(date, DateType.MONTH, num);
    }

    public static Date monthRoll(Integer num) {
        return monthRoll(new Date(), num);
    }

    public static Date monthRoll(Date date, Integer num) {
        return dateRoll(date, DateType.MONTH, num);
    }

    public static Date monthRollBack(Integer num) {
        return monthRollBack(new Date(), num);
    }

    public static Date monthRollBack(Date date, Integer num) {
        return dateRollBack(date, DateType.MONTH, num);
    }

    /**
     * 几天前/后的几点几时几分
     */
    public static Date dayAdd(Integer days, Integer hour, Integer minute, Integer second) {
        Calendar cal = DateUt.getCurrentDateTime(hour, minute, second);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();
    }

    /**
     * 到现在的年数
     */
    public static int fromYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return Calendar.getInstance().get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
    }

    /**
     * 一天的开始时间
     */
    public static Date dayStart(String day) {
        return str2date(day);
    }

    public static Date dayStart(Date day) {
        return getStartEndTime(day, DateType.DAY, false);
    }

    /**
     * 一天的最后时间
     */
    public static Date dayEnd(String day) {
        return dayEnd(str2date(day));
    }

    public static Date dayEnd(Date day) {
        return getStartEndTime(day, DateType.DAY, true);
    }

    public static Date dayEnd() {
        return getStartEndTime(null, DateType.DAY, true);
    }

    /**
     * 一个月的开始时间
     */
    public static Date monthStart(String month) {
        return str2month(month);
    }

    public static Date monthStart(Date date) {
        return getStartEndTime(date, DateType.MONTH, false);
    }

    public static Date monthStartBack(Integer num) {
        return monthStart(monthBack(num));
    }

    /**
     * 一个月的最后时间
     */
    public static Date monthEnd(String month) {
        return getStartEndTime(str2month(month), DateType.MONTH, true);
    }

    public static Date monthEnd(Date date) {
        return getStartEndTime(date, DateType.MONTH, true);
    }

    /**
     * 时间的开始结束时间
     */
    public static Date getStartEndTime(Date date, DateType dateType, boolean isEnd) {
        Calendar cal = null;
        if (isEnd) {
            cal = getCalendarTime(date, 23, 59, 59);
        } else {
            cal = getCalendarTime(date, 0, 0, 0);
        }
        switch (dateType) {
            case MONTH:
                cal.set(Calendar.DAY_OF_MONTH, isEnd ? cal.getActualMaximum(Calendar.DAY_OF_MONTH) : 1);
                break;
            default:
                break;
        }
        return cal.getTime();
    }

    /**
     * 两个时间相差
     */
    public static long between(Date start, Date end) {
        return end.getTime() - start.getTime();
    }
    // *********************** DurationFormatUtils ************************

    public static long fromDays(Date date) {
        return getDay(between(date, new Date()));
    }

    /**
     * 转换为对应值
     *
     * @param millis
     */
    public static long getSecond(final long millis) {
        return millis / DateUtils.MILLIS_PER_SECOND;
    }

    public static long getMinute(final long millis) {
        return millis / DateUtils.MILLIS_PER_MINUTE;
    }

    public static long getHour(final long millis) {
        return millis / DateUtils.MILLIS_PER_HOUR;
    }

    public static long getDay(final long millis) {
        return millis / DateUtils.MILLIS_PER_DAY;
    }

    /**
     * 持续时间格式化
     */
    public static String duration(final long millis, final String format) {
        return DurationFormatUtils.formatDuration(millis, format, false);
    }

    public static String duration(Date start, Date end) {
        return duration(between(start, end));
    }

    public static String duration(final long millis) {
        return formatDurationWords(millis, true, true);
    }

    private static String formatDurationWords(final long millis, final boolean moveLeft, final boolean moveRight) {
        String duration = duration(millis, "d' 天 'H' 小时 'm' 分 's' 秒'");
        if (moveLeft) {
            // this is a temporary marker on the front. Like ^ in regexp.
            duration = " " + duration;
            String tmp = StringUtils.replaceOnce(duration, " 0 天", StringUtils.EMPTY);
            if (tmp.length() != duration.length()) {
                duration = tmp;
                tmp = StringUtils.replaceOnce(duration, " 0 小时", StringUtils.EMPTY);
                if (tmp.length() != duration.length()) {
                    duration = tmp;
                    tmp = StringUtils.replaceOnce(duration, " 0 分", StringUtils.EMPTY);
                    duration = tmp;
                    if (tmp.length() != duration.length()) {
                        duration = StringUtils.replaceOnce(tmp, " 0 秒", StringUtils.EMPTY);
                    }
                }
            }
            if (duration.length() != 0) {
                // strip the space off again
                duration = duration.substring(1);
            }
        }
        if (moveRight) {
            String tmp = StringUtils.replaceOnce(duration, " 0 秒", StringUtils.EMPTY);
            if (tmp.length() != duration.length()) {
                duration = tmp;
                tmp = StringUtils.replaceOnce(duration, " 0 分", StringUtils.EMPTY);
                if (tmp.length() != duration.length()) {
                    duration = tmp;
                    tmp = StringUtils.replaceOnce(duration, " 0 小时", StringUtils.EMPTY);
                    if (tmp.length() != duration.length()) {
                        duration = StringUtils.replaceOnce(tmp, " 0 天", StringUtils.EMPTY);
                    }
                }
            }
        }
        // handle plurals
        duration = " " + duration;
        return duration.trim();
    }

    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    public static String month2str(Date date) {
        return date == null ? null : monthFormat.format(date);
    }

    public static Date str2month(String str) {
        try {
            return monthFormat.parse(str);
        } catch (Exception e) {
        }
        return null;
    }

    public static String date2str(Date date) {
        return date == null ? null : dateFormat.format(date);
    }

    public static Date str2date(String str) {
        try {
            return dateFormat.parse(str);
        } catch (Exception e) {
        }
        return null;
    }

    public static String minute2str(Date date) {
        return date == null ? null : minuteFormat.format(date);
    }

    public static Date str2minute(String str) {
        try {
            return minuteFormat.parse(str);
        } catch (Exception e) {
        }
        return null;
    }

    public static String second2str(Date date) {
        return date == null ? null : secondFormat.format(date);
    }

    public static Date str2second(String str) {
        try {
            return secondFormat.parse(str);
        } catch (Exception e) {
        }
        return null;
    }

    public static String time2str(Date date) {
        return date == null ? null : timeFormat.format(date);
    }

    public static Date str2time(String str) {
        try {
            return timeFormat.parse(str);
        } catch (Exception e) {
        }
        return null;
    }

}