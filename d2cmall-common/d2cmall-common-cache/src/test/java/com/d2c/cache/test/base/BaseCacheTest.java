package com.d2c.cache.test.base;

import com.d2c.cache.redis.RedisHandler;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-redis-product.xml"})
public abstract class BaseCacheTest extends AbstractJUnit4SpringContextTests {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected RedisHandler<String, Object> cache;

}
