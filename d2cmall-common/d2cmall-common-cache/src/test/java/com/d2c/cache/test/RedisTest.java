package com.d2c.cache.test;

import com.d2c.cache.test.base.BaseCacheTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisTest extends BaseCacheTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test() {
        logger.info("开始测试...");
        cache.set("test:123", "wull_123");
        logger.info("test..." + cache.get("test:123"));
        cache.delete("test*");
        logger.info("test2..." + cache.get("test:123"));
    }

}
