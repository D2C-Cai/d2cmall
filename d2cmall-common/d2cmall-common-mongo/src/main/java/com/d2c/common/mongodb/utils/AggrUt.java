package com.d2c.common.mongodb.utils;

import com.d2c.common.base.enums.DateType;

/**
 * MongoDb Aggregation 统计工具类
 *
 * @author wull
 */
public class AggrUt {

    public static final long GMT_CHINA_TIMEZONE = 28800000; //中国时差 +8:00 小时，补偿
    public static final String HOUR_FORMAT = "%H:%M";
    public static final String DAY_FORMAT = "%Y-%m-%d";
    public static final String WEEK_FORMAT = "%U周";
    public static final String MONTH_FORMAT = "%m月";
    public static final String YEAR_FORMAT = "%Y年";

    public static String dateAsFormat(DateType dateType) {
        switch (dateType) {
            case HOUR:
                return HOUR_FORMAT;
            case DAY:
                return DAY_FORMAT;
            case WEEK:
                return WEEK_FORMAT;
            case MONTH:
                return MONTH_FORMAT;
            case YEAR:
                return YEAR_FORMAT;
            default:
                return DAY_FORMAT;
        }
    }

}
