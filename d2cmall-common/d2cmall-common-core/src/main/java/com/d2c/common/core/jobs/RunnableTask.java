package com.d2c.common.core.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对应执行任务
 *
 * @author wull
 */
public abstract class RunnableTask<T> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public abstract void run(T bean);

}
