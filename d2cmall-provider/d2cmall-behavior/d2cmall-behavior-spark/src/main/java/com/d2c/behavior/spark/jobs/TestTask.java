package com.d2c.behavior.spark.jobs;

import com.d2c.behavior.spark.manage.TestManager;
import com.d2c.frame.spark.job.BaseSparkTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 商品推荐计算任务
 *
 * @author wull
 */
//@Component
public class TestTask extends BaseSparkTask {

    private static final long serialVersionUID = 5197040449544674906L;
    @Autowired
    private TestManager testManager;

    @Scheduled(fixedDelay = 10 * 1000)
    public void execute() {
        testManager.spark(null);
    }

}
