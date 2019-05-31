package com.d2c.common.core.cache.old;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class CacheTimerHandler {

    protected static final Log logger = LogFactory.getLog(CacheTimerHandler.class);
    private static final int SECOND_TIME = 1000;// 秒
    private static final int MIN_TIME = SECOND_TIME * 60;// 分
    public static final int DEFAULT_MIN_VALIDITY_TIME = MIN_TIME * 2;// 默认过期时间2分钟
    private static final Timer timer;
    private static final ConcurrentHashMap<String, CacheEntity<?>> map;
    private static CacheTimerHandler handle;

    static {
        timer = new Timer();
        map = new ConcurrentHashMap<String, CacheEntity<?>>();
    }

    private int defaultValidateTime = DEFAULT_MIN_VALIDITY_TIME;

    /**
     * 增加缓存对象
     *
     * @param key
     * @param ce
     */
    public static void addCache(String key, CacheEntity<?> ce) {
        addCache(key, ce, handle.defaultValidateTime);
    }

    /**
     * 增加缓存对象
     *
     * @param key
     * @param ce
     * @param validityTime 有效时间
     */
    public static void addCache(String key, CacheEntity<?> ce, int validityTime) {
        if (validityTime <= 0) {
            return;
        }
        map.put(key, ce);
        logger.debug("jvm cache add : " + key);
        // 添加过期定时
        timer.schedule(new TimeoutTimerTask(key), validityTime);
    }

    /**
     * 获取缓存对象
     *
     * @param key
     * @return
     */
    public static CacheEntity<?> getCache(String key) {
        if (handle.defaultValidateTime <= 0) {
            return null;
        }
        CacheEntity<?> value = map.get(key);
        logger.debug("jvm cache read : " + key);
        return value;
    }

    /**
     * 检查是否含有制定key的缓冲
     *
     * @param key
     * @return
     */
    public static boolean isConcurrent(String key) {
        return map.containsKey(key);
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    public static void removeCache(String key) {
        map.remove(key);
        logger.debug("jvm cache remove : " + key);
    }

    /**
     * 获取缓存大小
     *
     * @param key
     */
    public static int getCacheSize() {
        return map.size();
    }

    /**
     * 清除全部缓存
     */
    public static void clearCache() {
        if (null != timer) {
            timer.cancel();
        }
        map.clear();
    }

    /**
     * @param key
     * @param validatyTime 过期时间，分钟
     * @param cache
     * @return
     */
    public static <T> T getAndSetCacheValue(String key, CacheCallback<T> cache) {
        return getAndSetCacheValue(key, CacheTimerHandler.DEFAULT_MIN_VALIDITY_TIME, cache);
    }

    /**
     * @param key
     * @param validatyTime 过期时间，分钟
     * @param cache
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAndSetCacheValue(String key, int validatyTime, CacheCallback<T> cache) {
        CacheEntity<?> entity = CacheTimerHandler.getCache(key);
        if (entity == null) {
            T cacheResult = cache.doExecute();
            entity = new CacheEntity<T>(key, cacheResult);
            CacheTimerHandler.addCache(key, entity, MIN_TIME * validatyTime);
        }
        return (T) entity.getCacheContext();
    }

    public void init() {
        handle = this;
        handle.defaultValidateTime = this.defaultValidateTime * MIN_TIME;
    }

    public int getDefaultValidateTime() {
        return defaultValidateTime;
    }

    public void setDefaultValidateTime(int defaultValidateTime) {
        this.defaultValidateTime = defaultValidateTime;
    }

    /**
     * @className：TimeoutTimerTask
     * @description：清除超时缓存定时服务类 @remark：
     */
    static class TimeoutTimerTask extends TimerTask {

        private String ceKey;

        public TimeoutTimerTask(String key) {
            this.ceKey = key;
        }

        @Override
        public void run() {
            CacheTimerHandler.removeCache(ceKey);
            logger.debug("jvm cache remove : " + ceKey);
        }

    }

}