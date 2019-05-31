package com.d2c.common.api.service;

import com.d2c.common.api.dto.RelateDTO;

import java.util.List;

public interface RelateService<T, E, F> extends BaseService<T> {

    public List<F> getSlaveListByMasterId(Integer id);

    public void saveRelate(RelateDTO<T> relateList);

}
