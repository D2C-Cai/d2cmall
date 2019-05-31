package com.d2c.common.core.test;

import com.d2c.common.core.tools.LimitHashMap;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToolsTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test() {
        logger.info("********* 开始测试  *******");
        LimitHashMap<Integer, Integer> map = new LimitHashMap<>(100);
        for (int i = 0; i < 200; i++) {
            map.put(i, i);
        }
        logger.info("LimitHashMap ******* size: " + map.size());
        logger.info("LimitHashMap *******" + map);
    }

}
