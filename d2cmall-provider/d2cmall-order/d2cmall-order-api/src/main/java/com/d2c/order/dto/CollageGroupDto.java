package com.d2c.order.dto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.order.model.CollageGroup;
import com.d2c.order.model.CollageOrder;

import java.util.List;

public class CollageGroupDto extends CollageGroup {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 参团列表
     */
    private List<CollageOrder> collageOrders;

    public List<CollageOrder> getCollageOrders() {
        return collageOrders;
    }

    public void setCollageOrders(List<CollageOrder> collageOrders) {
        this.collageOrders = collageOrders;
    }

    public JSONObject toDtoJson() {
        JSONObject obj = super.toJson();
        JSONArray array = new JSONArray();
        if (this.getCollageOrders() != null) {
            this.getCollageOrders().forEach(item -> array.add(item.toJson()));
        }
        obj.put("collageOrders", array);
        return obj;
    }

}
