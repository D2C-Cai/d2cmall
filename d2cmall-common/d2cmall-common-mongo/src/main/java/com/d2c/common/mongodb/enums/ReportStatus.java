package com.d2c.common.mongodb.enums;

public enum ReportStatus {
    START("未完成"),
    END("已完成");
    String remark;

    ReportStatus(String remark) {
        this.remark = remark;
    }

    public static String getRemark(String name) {
        return ReportStatus.valueOf(name).getRemark();
    }

    public String getRemark() {
        return remark;
    }
}
