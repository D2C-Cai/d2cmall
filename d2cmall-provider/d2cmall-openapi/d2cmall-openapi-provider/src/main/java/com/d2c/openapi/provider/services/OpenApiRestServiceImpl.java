package com.d2c.openapi.provider.services;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.openapi.api.services.OpenApiRestService;

/**
 * 执行分销商任务
 *
 * @author wull
 */
@Service(protocol = {"dubbo", "rest"})
public class OpenApiRestServiceImpl implements OpenApiRestService {

    @Override
    public String test() {
        return null;
    }

}
