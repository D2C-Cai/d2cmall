package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;

public class ProductAttributeSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 参数组ID
     */
    private Long attributeGroupId;
    /**
     * 状态
     */
    private Integer enabled;

    public Long getAttributeGroupId() {
        return attributeGroupId;
    }

    public void setAttributeGroupId(Long attributeGroupId) {
        this.attributeGroupId = attributeGroupId;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

}
