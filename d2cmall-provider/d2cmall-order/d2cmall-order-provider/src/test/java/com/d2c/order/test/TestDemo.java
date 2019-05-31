package com.d2c.order.test;

import com.d2c.common.core.test.BaseTest;
import com.d2c.order.model.CollageOrder;
import com.d2c.order.service.CollageOrderService;
import com.d2c.order.service.PaymentService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestDemo extends BaseTest {

    @Autowired
    private CollageOrderService collageOrderService;
    @Autowired
    private PaymentService paymentService;

    @Test
    public void test() {
        CollageOrder order = collageOrderService.findBySn("Q15444995443891871");
        paymentService.doThirdRefund(order.getId(), order.getSn(), order.getPaymentId(), order.getPaySn(),
                order.getPaymentType(), order.getPaidAmount());
    }

}
