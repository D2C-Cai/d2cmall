package com.d2c.common.core.cache;

import com.d2c.common.base.thread.MyExecutors;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.base.utils.DateUt;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 本地缓存
 */
public class LocalCache {

    private static final long KEEP_TIME_DEF = 30 * 60 * 1000;
    // 守护进程执行周期
    private static final long WATCH_SECOND = 5 * 60;
    private static Map<String, CacheBean> map = new ConcurrentHashMap<>();
    private static ScheduledExecutorService watchThread = MyExecutors.newSingleScheduled();
    private static ScheduledFuture<?> watchFuture;

    /**
     * 获取缓存
     */
    public static Object get(String key) {
        CacheBean bean = map.get(key);
        if (bean == null) return null;
        if (bean.getOutTime() != null && bean.getOutTime() < System.currentTimeMillis()) {
            map.remove(key);
            return null;
        }
        checkWatch();
        return bean.getValue();
    }

    public static <T> T get(String key, Class<T> resClz) {
        return BeanUt.cast(get(key), resClz);
    }

    /**
     * 添加缓存
     */
    public static void set(String key, Object value) {
        set(key, value, KEEP_TIME_DEF);
    }

    public static void setInHours(String key, Object value, long hours) {
        set(key, value, hours * DateUt.HOUR_MILLIS);
    }

    public static void setInMinutes(String key, Object value, long minutes) {
        set(key, value, minutes * DateUt.MINUTE_MILLIS);
    }

    public static void setInSec(String key, Object value, long second) {
        set(key, value, second * DateUt.SECOND_MILLIS);
    }

    public static void set(String key, Object value, long keepTime) {
        checkWatch();
        map.put(key, new CacheBean(value, keepTime));
    }

    /**
     * 是否含有key
     */
    public static boolean containsKey(String key) {
        return map.containsKey(key);
    }

    /**
     * 删除缓存
     */
    public static void remove(String key) {
        map.remove(key);
    }

    /**
     * 缓存大小
     */
    public static int size() {
        return map.size();
    }

    /**
     * 清除全部缓存
     */
    public static void clear() {
        map.clear();
    }

    /**
     * 获得Map
     */
    public static Map<String, CacheBean> getMap() {
        return map;
    }

    /**
     * 检查并建立守护进程，保证内存数据会被清除，不会无限膨胀
     */
    private static void checkWatch() {
        if (watchFuture == null) {
            watchFuture = watchThread.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    if (!map.isEmpty()) {
                        long t = System.currentTimeMillis();
                        Iterator<Entry<String, CacheBean>> it = map.entrySet().iterator();
                        while (it.hasNext()) {
                            Entry<String, CacheBean> item = it.next();
                            if (item.getValue().getOutTime() < t) {
                                it.remove();
                            }
                        }
                    }
                }
            }, WATCH_SECOND, WATCH_SECOND, TimeUnit.SECONDS);
        }
    }

}
