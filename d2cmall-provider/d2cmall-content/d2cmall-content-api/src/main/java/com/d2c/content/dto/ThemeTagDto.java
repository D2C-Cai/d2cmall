package com.d2c.content.dto;

import com.alibaba.fastjson.JSONObject;
import com.d2c.content.model.ThemeTag;

public class ThemeTagDto extends ThemeTag {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", this.getId());
        json.put("name", this.getName());
        json.put("pic", this.getPic());
        json.put("count", this.getCount());
        return json;
    }

}
