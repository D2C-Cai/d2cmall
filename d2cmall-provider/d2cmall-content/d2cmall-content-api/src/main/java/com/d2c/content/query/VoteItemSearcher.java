package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

public class VoteItemSearcher extends BaseQuery {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String selectName;
    private String sort;
    private Long defId;

    public String getSelectName() {
        return selectName;
    }

    public void setSelectName(String selectName) {
        this.selectName = selectName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Long getDefId() {
        return defId;
    }

    public void setDefId(Long defId) {
        this.defId = defId;
    }

}
