package com.d2c.behavior.mongo.enums;

/**
 * 事件模式
 *
 * @author wull
 */
public enum EventMode {
    CHECK("页面点击");
    String remark;

    EventMode(String remark) {
        this.remark = remark;
    }

    public static String defaultMode() {
        return CHECK.name();
    }

    @Override
    public String toString() {
        return remark;
    }
}
