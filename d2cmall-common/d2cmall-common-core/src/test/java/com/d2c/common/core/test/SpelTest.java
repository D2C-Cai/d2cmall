package com.d2c.common.core.test;

import com.d2c.common.base.utils.SpelUt;
import com.d2c.common.core.test.bean.UserBean;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class SpelTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test() throws Exception {
        logger.info("********* 开始测试  *******");
        UserBean bean = new UserBean();
        bean.setUserName("wull");
        bean.setPrice(new BigDecimal(1000.0));
        bean.setCount(5);
        logger.info(SpelUt.parse("'这是个测试'", String.class));
        logger.info(SpelUt.parseStr("'这是个测试'+#userName", bean));
        logger.info("userName: " + SpelUt.parseBean("#{userName}", bean));
        logger.info("one price: " + SpelUt.parseBean("#{price / count}", bean));
    }

}
