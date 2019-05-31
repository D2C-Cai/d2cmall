package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.product.model.ProductRelation.RelationType;

public class RelationSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 类型
     */
    private RelationType type;
    /**
     * 源ID
     */
    private Long sourceId;
    /**
     * 关联ID
     */
    private Long relationId;

    public RelationType getType() {
        return type;
    }

    public void setType(RelationType type) {
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

}
