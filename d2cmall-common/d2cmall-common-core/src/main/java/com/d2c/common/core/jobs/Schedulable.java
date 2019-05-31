package com.d2c.common.core.jobs;

/**
 * 分发键值任务
 *
 * @author wull
 */
public interface Schedulable<T> {

    public Object getKey();

    public Class<? extends RunnableTask<T>> getRunTaskClz();

}
