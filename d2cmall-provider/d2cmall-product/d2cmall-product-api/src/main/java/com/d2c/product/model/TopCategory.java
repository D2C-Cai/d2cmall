package com.d2c.product.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 一级分类
 */
@Table(name = "p_product_top_category")
public class TopCategory extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    @AssertColumn("商品一级分类名称不能为空")
    private String name;
    /**
     * 代号
     */
    private String code;
    /**
     * 排序
     */
    private Integer sequence = 0;
    /**
     * 停用 0,启用 1
     */
    private Integer status = 1;
    /**
     * 图片
     */
    private String pic;
    /**
     * 英文名
     */
    private String eName;

    public TopCategory() {
    }

    ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", getId());
        obj.put("name", getName());
        return obj;
    }

}