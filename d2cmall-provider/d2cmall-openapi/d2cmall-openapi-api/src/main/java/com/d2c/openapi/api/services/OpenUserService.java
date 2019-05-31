package com.d2c.openapi.api.services;

import com.d2c.common.api.service.ListService;
import com.d2c.openapi.api.entity.OpenUserDO;

/**
 * OpenApi商家用户
 *
 * @author wull
 */
public interface OpenUserService extends ListService<OpenUserDO> {

    public OpenUserDO findByBrandId(Long brandId);

    public OpenUserDO findByToken(String token);

    public OpenUserDO register(OpenUserDO beans);

}
 