package com.d2c.flame.enums;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public enum TopCategoryEnum {
    FEMALE("女装", new Long[]{1L}, 10001L),
    MALE("男装", new Long[]{6L}, 10002L),
    KIDS("童装", new Long[]{7L}, 10005L),
    FURNITURE("生活家居", new Long[]{11L, 12L, 14L, 16L}, 10003L),
    SHOEBAG("鞋包", new Long[]{13L, 15L}, 10006L),
    JEWELRY("饰品", new Long[]{3L, 9L}, 10004L);
    private static Map<Long, TopCategoryEnum> topCodes;
    private static Map<Long, TopCategoryEnum> topIds;
    private static LinkedHashMap<Long, JSONObject> topLinkedMap;

    static {
        topCodes = new HashMap<Long, TopCategoryEnum>();
        topIds = new HashMap<Long, TopCategoryEnum>();
        topLinkedMap = new LinkedHashMap<Long, JSONObject>();
        for (TopCategoryEnum item : TopCategoryEnum.values()) {
            topCodes.put(item.getCode(), item);
            for (Long id : item.getIds()) {
                topIds.put(id, item);
            }
            JSONObject obj = new JSONObject();
            obj.put("id", item.getCode());
            obj.put("name", item.getName());
            topLinkedMap.put(item.getCode(), obj);
        }
    }

    private String name;
    private Long[] ids;
    private Long code;

    TopCategoryEnum(String name, Long[] ids, Long code) {
        this.name = name;
        this.ids = ids;
        this.code = code;
    }

    public static TopCategoryEnum getByCode(Long code) {
        return topCodes.get(code);
    }

    public static TopCategoryEnum getById(Long id) {
        return topIds.get(id);
    }

    public static LinkedHashMap<Long, JSONObject> getLinkedMap() {
        return topLinkedMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }
}
