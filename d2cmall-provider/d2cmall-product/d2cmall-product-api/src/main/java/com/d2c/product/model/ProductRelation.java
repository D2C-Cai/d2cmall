package com.d2c.product.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 商品搭配关系
 */
@Table(name = "p_product_relation")
public class ProductRelation extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 类型
     */
    private String type;
    /**
     * 组对象
     */
    @AssertColumn("商品不能为空")
    private Long sourceId;
    /**
     * 单一对象
     */
    @AssertColumn("商品关联不能为空")
    private Long relationId;
    /**
     * 排序号
     */
    private Integer sort = 0;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * PRODUCT 相关搭配, PRODUCTCOMB 组合商品
     */
    public static enum RelationType {
        PRODUCT, PRODUCTCOMB;

        public static RelationType geTypeByEnumName(String enumName) {
            if (enumName == null || enumName.length() == 0) {
                return null;
            }
            RelationType[] types = RelationType.values();
            for (RelationType type : types) {
                if (type.name().equals(enumName)) {
                    return type;
                }
            }
            return null;
        }

        public static RelationType fromString(String value) {
            try {
                return RelationType.valueOf(value.toUpperCase());
            } catch (Exception e) {
                throw new IllegalArgumentException(String.format("Invalid value '%s' for RelationType given!", value),
                        e);
            }
        }
    }

}
