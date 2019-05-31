package com.d2c.quartz.task.rest.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.quartz.task.ExpiredPartnerTask;
import com.d2c.quartz.task.rest.IDemoRestService;
import org.springframework.beans.factory.annotation.Autowired;

@Service(protocol = {"dubbo", "rest"})
public class DemoRestServiceImpl implements IDemoRestService {

    @Autowired
    private ExpiredPartnerTask task;

    @Override
    public void test() {
        task.execImpl();
    }

}
