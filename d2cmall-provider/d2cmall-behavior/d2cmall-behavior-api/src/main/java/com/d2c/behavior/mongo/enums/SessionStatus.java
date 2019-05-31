package com.d2c.behavior.mongo.enums;

/**
 * Session状态
 *
 * @author wull
 */
public enum SessionStatus {
    START("开始"), END("结束");
    String remark;

    SessionStatus(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return remark;
    }
}
