package com.d2c.behavior.services.rest;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.behavior.services.DepictService;
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
    private DepictService depictService;

    @Override
    public Object test() {
        logger.info("开始测试 ..");
        depictService.test();
        logger.info("主程序测试调用结束 ....666666666");
        return ResultHandler.success();
    }
//	public Object test() {
//		logger.info("开始测试 spark kafka..");
//		kafkaHandler.send(KafkaEnum.TestTopic, "wull test");
//		return ResultHandler.success();
//	}
}
