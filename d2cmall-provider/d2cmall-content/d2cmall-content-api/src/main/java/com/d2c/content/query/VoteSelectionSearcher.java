package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

public class VoteSelectionSearcher extends BaseQuery {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long defId;
    private String name;

    public Long getDefId() {
        return defId;
    }

    public void setDefId(Long defId) {
        this.defId = defId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
