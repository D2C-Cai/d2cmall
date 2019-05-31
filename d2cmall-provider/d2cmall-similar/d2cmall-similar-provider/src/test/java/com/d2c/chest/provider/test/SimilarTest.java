package com.d2c.chest.provider.test;

import com.d2c.common.core.test.BaseTest;
import com.d2c.common.math.basic.AvgHelper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class SimilarTest extends BaseTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test() {
        logger.debug("start a test");
        logger.debug("avgWeight:" + AvgHelper.avgWeight(Arrays.asList(0.5, 0.5), Arrays.asList(0.6, 0.4)));
    }

}
