package com.d2c.member.enums;

import com.d2c.util.date.DateUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 签到积分奖励
 *
 * @author wwn
 */
public enum SignRewardEnum {
    FIRST(1, 1), SECOND(2, 2), THIRD(3, 4), FORTH(4, 8), FIFTH(5, 16), SIXTH(6, 32), SEVENTH(7, 64);
    private static Date startDate520 = DateUtil.str2second("2019-05-13 00:00:00");
    private static Date endDate520 = DateUtil.str2second("2019-05-21 00:00:00");
    private static Map<Integer, Integer> holder = new HashMap<>();

    static {
        for (SignRewardEnum signReward : SignRewardEnum.values()) {
            holder.put(signReward.getDay(), signReward.getReward());
        }
    }

    private Integer day;
    private Integer reward;

    SignRewardEnum(Integer day, Integer reward) {
        this.day = day;
        this.reward = reward;
    }

    public static Map<Integer, Integer> getHolder() {
        Date now = new Date();
        if (now.after(startDate520) && now.before(endDate520)) {
            return SignRewardEnum520.getHolder();
        }
        return holder;
    }

    public static Integer getRewardByDay(Integer day) {
        Date now = new Date();
        if (now.after(startDate520) && now.before(endDate520)) {
            return SignRewardEnum520.getRewardByDay(day);
        }
        return holder.get(day);
    }

    public static boolean isPromotionFirstDay() {
        Date now = new Date();
        if (DateUtil.getStartOfDay(now).compareTo(startDate520) == 0) {
            return true;
        }
        return false;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    enum SignRewardEnum520 {
        FIRST(1, 100), SECOND(2, 100), THIRD(3, 200), FORTH(4, 100), FIFTH(5, 100), SIXTH(6, 100), SEVENTH(7, 500);
        private static Map<Integer, Integer> holder = new HashMap<>();

        static {
            for (SignRewardEnum520 signReward : SignRewardEnum520.values()) {
                holder.put(signReward.getDay(), signReward.getReward());
            }
        }

        private Integer day;
        private Integer reward;

        SignRewardEnum520(Integer day, Integer reward) {
            this.day = day;
            this.reward = reward;
        }

        public static Map<Integer, Integer> getHolder() {
            return holder;
        }

        public static Integer getRewardByDay(Integer day) {
            return holder.get(day);
        }

        public Integer getDay() {
            return day;
        }

        public void setDay(Integer day) {
            this.day = day;
        }

        public Integer getReward() {
            return reward;
        }

        public void setReward(Integer reward) {
            this.reward = reward;
        }
    }
}
