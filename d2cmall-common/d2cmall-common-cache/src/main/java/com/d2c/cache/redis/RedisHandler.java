package com.d2c.cache.redis;

import com.d2c.cache.redis.constant.CacheConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class RedisHandler<K, V> {

    @Autowired
    private RedisTemplate<K, V> redisTemplate;

    public V get(K key) {
        return opsForValue().get(key);
    }

    /**
     * 获取数据，如存在则重置过期时间
     */
    public V getAndExpire(K key, long timeout, TimeUnit unit) {
        V bean = get(key);
        if (bean != null) {
            expire(key, timeout, unit);
        }
        return bean;
    }

    public void set(K key, V value) {
        opsForValue().set(key, value);
    }

    public void setInFast(K key, V value) {
        setInMinutes(key, value, CacheConst.CACHE_KEEP_FAST);
    }

    public void setInNormal(K key, V value) {
        setInMinutes(key, value, CacheConst.CACHE_KEEP_NORMAL);
    }

    public void setInHours(K key, V value, long hours) {
        opsForValue().set(key, value, hours, TimeUnit.HOURS);
    }

    public void setInMinutes(K key, V value, long minutes) {
        opsForValue().set(key, value, minutes, TimeUnit.MINUTES);
    }

    public void setInSec(K key, V value, long timeout) {
        opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    public void set(K key, V value, long timeout, TimeUnit unit) {
        opsForValue().set(key, value, timeout, unit);
    }

    public void delete(K key) {
        redisTemplate.delete(key);
    }

    public void delete(Collection<K> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 设置过期时间
     */
    public Boolean expire(K key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    public Boolean expireSecond(K key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }
    // **********************************************************

    public ValueOperations<K, V> opsForValue() {
        return redisTemplate.opsForValue();
    }

    public RedisTemplate<K, V> getRedis() {
        return redisTemplate;
    }

    public void setRedis(RedisTemplate<K, V> redis) {
        this.redisTemplate = redis;
    }

}
