package com.d2c.common.core.jobs;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 队列任务
 *
 * @author wull
 */
public abstract class BaseQueueTask<T> extends BaseTask {

    private final static ExecutorService pools = Executors.newCachedThreadPool();
    //  高并发同步队列
//	protected Queue<T> queue = new ConcurrentLinkedQueue<>();
    protected Queue<T> queue = new LinkedList<>();

    public void add(T bean) {
        queue.add(bean);
    }

    @Override
    public void run() {
        while (true) {
            T bean = queue.poll();
            if (bean == null) break;
            pools.execute(() -> {
//				logger.debug("异步队列任务开始执行.." + bean);
                run(bean);
            });
        }
    }

    public abstract void run(T bean);

}
