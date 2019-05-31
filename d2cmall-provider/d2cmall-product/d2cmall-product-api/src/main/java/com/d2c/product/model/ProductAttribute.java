package com.d2c.product.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

/**
 * 商品参数
 */
@Table(name = "p_product_attribute")
public class ProductAttribute extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    @AssertColumn("属性名称不能为空")
    private String name;
    /**
     * 排序
     */
    private Integer sequence = 0;
    /**
     * 是否必须
     */
    private Integer required = 0;
    /**
     * 是否启用
     */
    private Integer enabled = 0;
    /**
     * 可选项
     */
    private String attributeOptions;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 属性组ID
     */
    private Long attributeGroupId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Long getAttributeGroupId() {
        return attributeGroupId;
    }

    public void setAttributeGroupId(Long attributeGroupId) {
        this.attributeGroupId = attributeGroupId;
    }

    public Integer getRequired() {
        return required;
    }

    public void setRequired(Integer required) {
        this.required = required;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getAttributeOptions() {
        return attributeOptions;
    }

    public void setAttributeOptions(String attributeOptions) {
        this.attributeOptions = attributeOptions;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public ProductAttribute fromHashMap(HashMap<String, Object> map) {
        ProductAttribute arrtibute = new ProductAttribute();
        arrtibute.setId((Long) map.get("id"));
        arrtibute.setName((String) map.get("name"));
        arrtibute.setEnabled((int) map.get("enabled"));
        arrtibute.setType(((Integer) map.get("type")));
        return arrtibute;
    }

    public String[] getAttributeOptionsList() {
        if (this.attributeOptions != null) {
            return attributeOptions.split(",");
        }
        return null;
    }

    public void setAttributeOptionsList() {
    }

    /**
     * 定义类型
     */
    public enum AttributeType {
        text(0), number(1), alphaint(2), select(3), checkbox(4), date(5);
        private static Map<Integer, AttributeType> attributeTypeMap = new HashMap<Integer, AttributeType>();

        static {
            attributeTypeMap.put(0, AttributeType.text);
            attributeTypeMap.put(1, AttributeType.number);
            attributeTypeMap.put(2, AttributeType.alphaint);
            attributeTypeMap.put(3, AttributeType.select);
            attributeTypeMap.put(4, AttributeType.checkbox);
            attributeTypeMap.put(5, AttributeType.date);
        }

        private int code;

        AttributeType(int code) {
            this.code = code;
        }

        public static AttributeType getAttributeType(int code) {
            return attributeTypeMap.get(code);
        }

        public int getCode() {
            return code;
        }
    }

}