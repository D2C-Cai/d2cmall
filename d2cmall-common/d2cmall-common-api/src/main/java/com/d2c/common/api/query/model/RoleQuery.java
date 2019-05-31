package com.d2c.common.api.query.model;

import java.util.List;

public interface RoleQuery {

    void setStoreId(Long storeId);

    void setBrandIds(List<Long> brandIds);

}
