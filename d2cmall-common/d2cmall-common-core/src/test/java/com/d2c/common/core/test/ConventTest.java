package com.d2c.common.core.test;

import com.d2c.common.core.test.bean.UserBean;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;

public class ConventTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test() {
        logger.info("********* 开始测试  *******");
        UserBean bean = new UserBean();
        bean.setUserName("wull");
        bean.setPrice(new BigDecimal(1000.0));
//		bean.setCount(5);
        bean.setLoginCode("1234567890");
        bean.setDate(new Date());
    }

}
