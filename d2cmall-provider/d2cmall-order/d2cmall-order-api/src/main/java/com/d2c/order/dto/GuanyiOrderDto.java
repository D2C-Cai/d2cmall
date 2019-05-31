package com.d2c.order.dto;

import com.alibaba.fastjson.JSONObject;
import com.d2c.order.model.GuanyiOrder;
import com.d2c.order.model.GuanyiOrderItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuanyiOrderDto extends GuanyiOrder {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private List<GuanyiOrderItem> items;

    public GuanyiOrderDto() {
        super();
    }

    public GuanyiOrderDto(JSONObject obj) {
        super(obj);
    }

    public List<GuanyiOrderItem> getItems() {
        return items;
    }

    public void setItems(List<GuanyiOrderItem> items) {
        this.items = items;
    }

    public enum ShopCodeEnum {
        // 管易门店编号对应的官网门店code
        D2C001("001"), D2C002(null), D2C003("D2C003"), D2C004(null), D2C005("D2C005"), D2C006("D2C006"),
        D2C007("D2C101"), D2C008("0023"), D2C009("D2C101"), D2C010("D2C101"), D2C011("D2C011"), D2C013("D2C100"),
        D2C014("D2C100"), D2C015("D2C100"), D2C016("D2C100"), D2C017("D2C100"), D2C018("D2C100"), D2C023("D2C100"),
        D2C022("D2C022"), D2C024(null), D2C025("D2C025");
        private static Map<String, ShopCodeEnum> shopCodeMap = new HashMap<>();

        static {
            for (ShopCodeEnum shopCodeEnum : ShopCodeEnum.values()) {
                shopCodeMap.put(shopCodeEnum.name(), shopCodeEnum);
            }
        }
        ;

        private String storeCode;

        ShopCodeEnum(String storeCode) {
            this.storeCode = storeCode;
        }

        public static Map<String, ShopCodeEnum> getShopCodeMap() {
            return shopCodeMap;
        }

        public static void setShopCodeMap(Map<String, ShopCodeEnum> shopCodeMap) {
            ShopCodeEnum.shopCodeMap = shopCodeMap;
        }

        public String getStoreCode() {
            return storeCode;
        }
    }

}
