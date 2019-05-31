package com.d2c.member.enums;

import com.d2c.common.base.utils.StringUt;

/**
 * 用户完成任务后奖励
 */
public enum MemberTaskAfterEnum {
    INTEGRATION("积分"), LOTTERY_CHANCE("抽奖机会");
    String show;

    MemberTaskAfterEnum(String show) {
        this.show = show;
    }

    public static MemberTaskAfterEnum getValueOf(String name) {
        try {
            if (StringUt.isBlank(name)) {
                return INTEGRATION;
            }
            return valueOf(name);
        } catch (Exception e) {
            return INTEGRATION;
        }
    }

    @Override
    public String toString() {
        return show;
    }
}
