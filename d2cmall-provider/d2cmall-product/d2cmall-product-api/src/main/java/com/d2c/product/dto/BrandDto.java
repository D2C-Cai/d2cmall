package com.d2c.product.dto;

import com.alibaba.fastjson.JSONObject;
import com.d2c.product.model.Brand;
import com.d2c.product.model.BrandTag;

import java.util.List;

public class BrandDto extends Brand {

    private static final long serialVersionUID = 1L;
    /**
     * 使用的标签
     */
    private List<BrandTag> usedTags;
    /**
     * 是否是当前用户关注的设计师
     */
    private Integer attentioned = 0;

    public List<BrandTag> getUsedTags() {
        return usedTags;
    }

    public void setUsedTags(List<BrandTag> usedTags) {
        this.usedTags = usedTags;
    }

    public Integer getAttentioned() {
        return attentioned;
    }

    public void setAttentioned(Integer attentioned) {
        this.attentioned = attentioned;
    }

    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("attentioned", this.getAttentioned());
        return json;
    }

}
