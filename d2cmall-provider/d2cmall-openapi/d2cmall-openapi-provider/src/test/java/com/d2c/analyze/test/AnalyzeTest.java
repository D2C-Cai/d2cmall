package com.d2c.analyze.test;

import com.d2c.common.base.enums.DateType;
import com.d2c.common.base.utils.DateUt;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalyzeTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test() {
        logger.info("开始执行测试...." + DateUt.second2str(DateUt.dateBack(DateType.YEAR, 1)));
    }

}
