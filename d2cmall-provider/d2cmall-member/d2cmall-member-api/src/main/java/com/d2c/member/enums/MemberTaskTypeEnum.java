package com.d2c.member.enums;

public enum MemberTaskTypeEnum {
    COMMON_TASK("日常任务"),
    DAY_TASK("每日任务"),
    NEW_TASK("新手任务"),
    TIME_TASK("限时任务");
    String display;

    MemberTaskTypeEnum(String display) {
        this.display = display;
    }

    public static boolean isCommonType(String name) {
        return COMMON_TASK.equals(getValueOf(name));
    }

    public static MemberTaskTypeEnum getValueOf(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return display;
    }
}