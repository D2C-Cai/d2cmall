package com.d2c.openapi.api.query;

import com.d2c.common.api.annotation.search.SearchField;
import com.d2c.common.api.query.enums.OperType;
import com.d2c.common.api.query.model.BaseQuery;

public class OpenUserQuery extends BaseQuery {

    private static final long serialVersionUID = -3252938727119944601L;
    /**
     * token
     */
    @SearchField
    private String token;
    /**
     * 品牌名称
     */
    @SearchField(oper = OperType.LIKE)
    private String brandName;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

}
