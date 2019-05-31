package com.d2c.behavior.mongo.enums;

/**
 * 事件模式
 *
 * @author wull
 */
public enum EventQueryType {
    UV("人数"), PV("次数");
    String display;

    EventQueryType(String display) {
        this.display = display;
    }

    public static EventQueryType getValueOf(String name) {
        try {
            return EventQueryType.valueOf(name);
        } catch (Exception e) {
            return UV;
        }
    }

    @Override
    public String toString() {
        return display;
    }

    public boolean isUv() {
        return this == UV;
    }

    public boolean isPv() {
        return this == PV;
    }
}
