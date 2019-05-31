package com.d2c.member.enums;

import java.math.BigDecimal;

public enum PointRuleTypeEnum {
    REGISTER("注册", 1), FIRST("首单", 1), SIGN("签到", 1), EXCHANGE("兑换", -1),
    COMMENT("评价", 1), RECOMEND("推评", 1), MEMBERSHARE("买家秀", 1), TASK("日常任务", 1), COLLECTIONCARD("集卡活动", 1),
    ORDER("订单", 1) {
        @Override
        public int calculatePoint(String ratio, BigDecimal amount) {
            int point = 0;
            if (amount != null && amount.compareTo(new BigDecimal(0)) > 0) {
                String[] ratios = ratio.split(":");
                if (ratios.length > 1) {
                    point = amount.multiply(new BigDecimal(ratios[1])).divide(new BigDecimal(ratios[0])).intValue();
                }
            }
            return point;
        }
    };
    private String display;
    private Integer direction;

    PointRuleTypeEnum(String display, Integer direction) {
        this.display = display;
        this.direction = direction;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public int calculatePoint(String ratios, BigDecimal amount) {
        return Integer.parseInt(ratios);
    }
}
