package com.d2c.common.core.test;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:applicationcontext-test.xml"})
@Transactional("transactionManager")
public abstract class SuperTest extends AbstractJUnit4SpringContextTests {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public static void init() {
    }

}
