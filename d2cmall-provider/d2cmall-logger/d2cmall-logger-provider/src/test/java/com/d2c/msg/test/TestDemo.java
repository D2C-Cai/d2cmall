package com.d2c.msg.test;

import com.d2c.common.core.test.BaseTest;
import com.d2c.logger.service.SmsLogService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestDemo extends BaseTest {

    @Autowired
    private SmsLogService smsLogService;

    @Test
    public void test() {
        smsLogService.doSendSms("886", "986662144", "您好，这是一个D2C海外短信测试消息，如果收到请忽略，十分抱歉对您的打扰！");
    }

}
