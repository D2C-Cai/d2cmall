package com.d2c.product.test;

import com.d2c.common.base.exception.AssertException;
import com.d2c.common.core.test.BaseTest;
import com.d2c.mybatis.handler.ValidateHandler;
import com.d2c.product.model.Present;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class TestDemo extends BaseTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test() {
        logger.info("开始执行测试....");
        ValidateHandler vh = new ValidateHandler();
        Present bean = new Present();
        bean.setName("111");
        bean.setPrice(new BigDecimal(0));
        try {
            vh.check(bean);
        } catch (AssertException e) {
            logger.error("提示错误:" + e.getMessage(), e);
        }
    }

}