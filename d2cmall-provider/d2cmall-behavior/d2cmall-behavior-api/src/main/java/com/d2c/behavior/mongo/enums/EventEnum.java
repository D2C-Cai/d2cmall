package com.d2c.behavior.mongo.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 事件模式
 * <p> 事件在声明后才会被记录
 *
 * @author wull
 */
public enum EventEnum {
    PRODUCT("打开商品详情"),
    PARTNER_VISIT("买手店-访问");
    static List<String> events;
    String event;

    EventEnum(String event) {
        this.event = event;
    }

    public static boolean contains(String event) {
        return eventNames().contains(event);
    }

    public static List<String> eventNames() {
        if (events == null) {
            events = new ArrayList<String>();
            for (EventEnum em : values()) {
                events.add(em.event);
            }
        }
        return events;
    }

    public boolean eq(String event) {
        return this.event.equals(event);
    }

    @Override
    public String toString() {
        return event;
    }
}
