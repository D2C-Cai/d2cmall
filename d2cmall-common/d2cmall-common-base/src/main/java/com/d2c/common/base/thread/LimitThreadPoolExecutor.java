package com.d2c.common.base.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LimitThreadPoolExecutor extends ThreadPoolExecutor {

    public LimitThreadPoolExecutor(int maxPoolSize) {
        super(maxPoolSize, maxPoolSize, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }

}
