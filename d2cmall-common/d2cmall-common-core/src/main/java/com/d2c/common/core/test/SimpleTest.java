package com.d2c.common.core.test;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SimpleTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public static void init() {
    }

}
