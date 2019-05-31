package com.d2c.product.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 销售属性
 */
@Table(name = "p_product_salesproperty")
public class SalesProperty extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    @AssertColumn("商品销售属性名称不能为空")
    private String name;
    /**
     * 属性值
     */
    @AssertColumn("商品销售属性值不能为空")
    private String value;
    /**
     * 编码
     */
    @AssertColumn("商品销售属性编码不能为空")
    private String code;
    /**
     * 种类1,2,3 对应商品销售属性1,2,3
     */
    @AssertColumn("商品销售属性类型不能为空")
    private Integer type;
    /**
     * 属性组ID
     */
    private Long groupId = 0L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", getId());
        obj.put("code", getCode());
        obj.put("value", getValue());
        return obj;
    }

    public static enum SalesPropertyType {
        COLOR(1), SIZE(2), DF(3);
        private int code;

        SalesPropertyType() {
        }

        SalesPropertyType(int code) {
            this.code = code;
        }

        public static SalesPropertyType getType(int code) {
            switch (code) {
                case 1:
                    return COLOR;
                case 2:
                    return SIZE;
                case 3:
                    return DF;
                default:
                    break;
            }
            return DF;
        }

        public SalesPropertyType valueOf(int code) {
            return SalesPropertyType.getType(code);
        }

        public int getCode() {
            return code;
        }
    }

}
