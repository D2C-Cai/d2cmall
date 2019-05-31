package com.d2c.flame.enums;

public enum ProductCategoryEnum {
    FEMALE("0001", "女装"),
    JEWELRY("0003", "首饰"),
    ACC("0005", "配饰"),
    MALE("0006", "男装"),
    KIDS("0007", "童装"),
    PERSONAL("0010", "家居个护"),
    FURNITURE("0011", "居家生活"),
    SHOE("0012", "鞋履"),
    COSMETIC("0013", "彩妆美妆"),
    BAG("0014", "箱包"),
    APPLIANCES("0015", "创意家电");
    Long topId;
    String code;
    String name;

    ProductCategoryEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Long getTopId() {
        return topId;
    }

    public void setTopId(Long topId) {
        this.topId = topId;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
