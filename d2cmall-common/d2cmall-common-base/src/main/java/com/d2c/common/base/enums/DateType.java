package com.d2c.common.base.enums;

public enum DateType {
    HOUR("小时", 24), DAY("天", 30), WEEK("周", 10), MONTH("月", 12), YEAR("年", 3);
    String display;
    Integer defaultNum;

    DateType(String display, Integer defaultNum) {
        this.display = display;
        this.defaultNum = defaultNum;
    }

    public static DateType getValueOf(String name) {
        try {
            return DateType.valueOf(name);
        } catch (Exception e) {
            return DAY;
        }
    }

    @Override
    public String toString() {
        return display;
    }

    public Integer getDefaultNum() {
        return defaultNum;
    }
}
