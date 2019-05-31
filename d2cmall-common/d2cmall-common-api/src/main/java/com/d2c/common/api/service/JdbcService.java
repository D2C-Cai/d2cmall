package com.d2c.common.api.service;

import java.util.List;

public interface JdbcService<T> {

    public List<T> findList(String sql);

}
