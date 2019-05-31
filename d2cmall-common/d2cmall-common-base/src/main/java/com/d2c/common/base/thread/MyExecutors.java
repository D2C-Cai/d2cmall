package com.d2c.common.base.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyExecutors {

    private final static Logger logger = LoggerFactory.getLogger(MyExecutors.class);
    private static ExecutorService limilPool;
    private static ExecutorService cachePool;

    public static ExecutorService limilPool() {
        if (limilPool == null) {
            limilPool = newLimit(20);
        }
        return limilPool;
    }

    public static ExecutorService cachePool() {
        if (cachePool == null) {
            cachePool = Executors.newCachedThreadPool();
        }
        return cachePool;
    }
    // ********************************************

    public static ExecutorService newLimit(int maxPoolSize) {
        return new LimitThreadPoolExecutor(maxPoolSize);
    }

    public static ExecutorService newSingle() {
        return Executors.newSingleThreadExecutor();
    }

    public static ScheduledExecutorService newSingleScheduled() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    public static ScheduledExecutorService newScheduled(int corePoolSize) {
        return Executors.newScheduledThreadPool(corePoolSize);
    }

    public static void awaitTermination(ExecutorService pools) {
        try {
            logger.info("开始等待全部线程到达...");
            pools.shutdown();
            while (pools.isTerminated()) {
                pools.awaitTermination(100, TimeUnit.MILLISECONDS);
            }
            logger.info("全部线程已经到达，继续下一步...");
        } catch (InterruptedException e) {
            logger.error("线程池同步终止异常...", e);
        }
    }

}
