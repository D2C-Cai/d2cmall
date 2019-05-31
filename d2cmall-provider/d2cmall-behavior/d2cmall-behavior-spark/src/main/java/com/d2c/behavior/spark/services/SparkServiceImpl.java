package com.d2c.behavior.spark.services;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.behavior.api.services.rest.SparkService;
import com.d2c.behavior.spark.manage.DepictManager;
import com.d2c.common.api.response.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * REST服务
 *
 * @author wull
 */
@Service(protocol = {"dubbo", "rest"})
public class SparkServiceImpl implements SparkService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DepictManager depictManager;

    @Override
    public Object test() {
        depictManager.depictData(null);
        return ResultHandler.success();
    }

}
