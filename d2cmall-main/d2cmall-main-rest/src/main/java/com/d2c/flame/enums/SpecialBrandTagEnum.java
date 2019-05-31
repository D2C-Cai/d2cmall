package com.d2c.flame.enums;

import java.util.HashMap;
import java.util.Map;

public enum SpecialBrandTagEnum {
    MALE(10001L, "男士品牌上新"),
    FEMALE(10002L, "女士品牌上新"),
    FURNITURE(10003L, "乐享生活品牌上新"),
    BAG(10004L, "鞋包品牌上新");
    private static Map<Long, String> map;

    static {
        map = new HashMap<Long, String>();
        for (SpecialBrandTagEnum a : values()) {
            map.put(a.getCode(), a.toString());
        }
    }

    private Long code;
    private String name;

    SpecialBrandTagEnum(Long code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 判断是否是特殊标签
     *
     * @param id
     * @return
     */
    public static boolean isBrand4NewProduct(Long id) {
        return map.containsKey(id);
    }

    /**
     * 获取标签名
     *
     * @param id
     * @return
     */
    public static String getSubModuleName(Long id) {
        return map.get(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }
}