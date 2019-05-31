package com.d2c.common.core.jobs;

import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.BeanUt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 队列任务调度
 *
 * @author wull
 */
public abstract class BaseQueueScheduler<T> extends BaseQueueTask<T> {

    private Map<Object, Class<? extends RunnableTask<T>>> scheMap;

    /**
     * 自动调度分发任务
     */
    @Override
    public void run(T bean) {
        if (scheMap == null) {
            List<Schedulable<T>> list = getSchedulableList();
            AssertUt.notNull(list);
            scheMap = new HashMap<>();
            for (Schedulable<T> sche : list) {
                scheMap.put(sche.getKey(), sche.getRunTaskClz());
            }
        }
        BeanUt.newInstance(scheMap.get(getKey(bean))).run(bean);
    }

    public abstract List<Schedulable<T>> getSchedulableList();

    public abstract Object getKey(T bean);

}
