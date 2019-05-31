package com.d2c.main.openapi.manager;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.cache.redis.annotation.CacheMethod;
import com.d2c.openapi.api.entity.OpenUserDO;
import com.d2c.openapi.api.services.OpenUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OpenUserManager {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Reference
    private OpenUserService openUserService;

    /**
     * 验证Token
     */
    @CacheMethod
    public OpenUserDO findByToken(String token) {
        return openUserService.findByToken(token);
    }

}
