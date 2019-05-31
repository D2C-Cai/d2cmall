package com.d2c.common.base.utils;

import org.apache.commons.lang3.math.NumberUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 数值转换工具类
 *
 * @author wull
 */
public class MathUt {

    public final static DecimalFormat percentFormat = new DecimalFormat("#.##%");
    public final static DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    public final static DecimalFormat threeDecimalFormat = new DecimalFormat("#0.000");
    public final static DecimalFormat decimalLikeFormat = new DecimalFormat("#.##");

    /**
     * 对象是否可转换为数值
     */
    public static boolean isNumber(String str) {
        return str != null && NumberUtils.isCreatable(str);
    }

    public static boolean isNumber(Object obj) {
        return obj != null && obj instanceof Number;
    }
    //****************** Format *********************

    public static String toPercent(Number num) {
        return percentFormat.format(num);
    }

    public static String toStr(Double num) {
        return decimalFormat.format(num);
    }

    public static String double2str3(Double num) {
        return threeDecimalFormat.format(num);
    }

    public static String double2strlink(Double num) {
        return decimalLikeFormat.format(num);
    }

    public static List<Long> str2long(List<String> objs) {
        if (objs == null) {
            return null;
        }
        List<Long> list = new ArrayList<Long>(objs.size());
        if (objs != null && !objs.isEmpty()) {
            Iterator<String> it = objs.iterator();
            while (it.hasNext()) {
                list.add(Long.parseLong(it.next()));
            }
        }
        return list;
    }

    public static List<Integer> str2int(List<String> objs) {
        if (objs == null) {
            return null;
        }
        List<Integer> list = new ArrayList<Integer>(objs.size());
        if (objs != null && !objs.isEmpty()) {
            Iterator<String> it = objs.iterator();
            while (it.hasNext()) {
                list.add(Integer.parseInt(it.next()));
            }
        }
        return list;
    }

}
