package com.d2c.behavior.test;

import com.d2c.behavior.services.EventQueryServiceImpl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.utils.JsonUt;
import com.d2c.common.core.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class CacheTest extends BaseTest {

    @Autowired
    private EventQueryServiceImpl eventQueryService;

    @Test
    public void test() throws IOException {
        logger.info("开始执行测试....");
        PageResult<?> res = eventQueryService.findProductVisit(3031125L, new PageModel());
        logger.info("**********数据返回 PageResult: " + JsonUt.serialize(res));
    }

}
