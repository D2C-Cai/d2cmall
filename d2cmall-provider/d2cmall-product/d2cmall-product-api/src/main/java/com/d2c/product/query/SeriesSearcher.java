package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.common.api.query.model.RoleQuery;

import java.util.List;

public class SeriesSearcher extends BaseQuery implements RoleQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String name;
    /**
     * 品牌ID
     */
    private Long designerId;
    /**
     * 品牌ID
     */
    private List<Long> designerIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public List<Long> getDesignerIds() {
        return designerIds;
    }

    public void setDesignerIds(List<Long> designerIds) {
        this.designerIds = designerIds;
    }

    @Override
    public void setStoreId(Long storeId) {
    }

    @Override
    public void setBrandIds(List<Long> brandIds) {
        this.designerIds = brandIds;
    }

}
