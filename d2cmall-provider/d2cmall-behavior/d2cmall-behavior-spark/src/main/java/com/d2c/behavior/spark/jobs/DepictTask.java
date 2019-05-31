package com.d2c.behavior.spark.jobs;

import com.d2c.behavior.spark.manage.DepictManager;
import com.d2c.frame.spark.job.BaseSparkTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 商品推荐计算任务
 *
 * @author wull
 */
@Component
public class DepictTask extends BaseSparkTask {

    private static final long serialVersionUID = 5197040449544674906L;
    @Autowired
    private DepictManager depictManager;

    //	@Scheduled(cron = "0 25 0/1 * * ?")
    @Scheduled(fixedDelay = 300 * 1000)
    public void execute() {
        depictManager.depictData(null);
    }

}
