package com.d2c.common.core.test;

import com.d2c.common.base.utils.ThreadUt;
import com.d2c.common.core.cache.LocalCache;
import com.d2c.common.core.test.bean.UserBean;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class LocalCacheTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test() {
        logger.info("********* 开始测试  *******");
        UserBean bean = new UserBean();
        bean.setUserName("wull");
        bean.setPrice(new BigDecimal(1000.0));
        bean.setCount(5);
        LocalCache.set("bean", bean, 2000);
        LocalCache.set("bean11", bean, 8000);
        for (int i = 0; i < 15; i++) {
            logger.info("bean: " + LocalCache.get("bean11", UserBean.class));
            logger.info("map: " + LocalCache.getMap());
            ThreadUt.sleep(1000);
        }
        logger.info("********* 停止测试  *******");
    }

}
