package com.d2c.member.mongo.enums;

/**
 * 任务状态
 *
 * @author wull
 */
public enum MemberTaskState {
    TODO_TASK("做任务"),
    AWARD_TASK("领取奖励"),
    DONE_TASK("已完成");
    String display;

    MemberTaskState(String display) {
        this.display = display;
    }

    /**
     * 是否已领取奖励
     */
    public static boolean isAward(String name) {
        return DONE_TASK.equals(getValueOf(name));
    }

    public static boolean isDone(String name) {
        return !TODO_TASK.equals(getValueOf(name));
    }

    public static MemberTaskState getValueOf(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return TODO_TASK;
        }
    }

    @Override
    public String toString() {
        return display;
    }
}