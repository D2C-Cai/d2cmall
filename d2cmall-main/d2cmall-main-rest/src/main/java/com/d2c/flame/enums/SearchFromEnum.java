package com.d2c.flame.enums;

public enum SearchFromEnum {
    RECOM("推荐商品"),
    SIMILAR_VISIT("浏览相似商品"),
    SIMILAR_PRODUCT("加购收藏相似商品");
    String name;

    SearchFromEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
