package com.d2c.order.test;

import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.base.utils.JsonUt;
import com.d2c.common.core.test.BaseTest;
import com.d2c.member.view.MemberInfoVO;
import com.d2c.order.service.OrderQueryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ConvertTest extends BaseTest {

    @Autowired
    private OrderQueryService orderQueryService;

    @Test
    public void test() {
        logger.info("开始执行测试....");
        MemberInfoVO view = BeanUt.apply(new MemberInfoVO(), orderQueryService.findOrderStat(908163L));
        logger.info("view...." + JsonUt.serialize(view));
    }

}
