package com.d2c.common.core.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseTask {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public abstract void run();

    public void doExecute() {
        try {
//			logger.info("开始执行任务...");
            run();
        } catch (Exception e) {
            logger.error("任务执行失败...", e);
        }
    }

}
