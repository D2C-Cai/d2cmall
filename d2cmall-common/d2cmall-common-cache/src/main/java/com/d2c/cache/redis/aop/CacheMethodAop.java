package com.d2c.cache.redis.aop;

import com.d2c.cache.redis.RedisHandler;
import com.d2c.cache.redis.annotation.CacheMethod;
import com.d2c.common.base.utils.AnnUt;
import com.d2c.common.base.utils.SpelUt;
import com.d2c.common.base.utils.StringUt;
import com.d2c.common.core.aop.BaseAop;
import com.d2c.common.core.cache.LocalCache;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 方法缓存AOP切片处理类
 *
 * @author wull
 */
@Aspect
public class CacheMethodAop extends BaseAop {

    @Autowired
    private RedisHandler<String, Object> cache;

    /**
     * 缓存返回数据
     *
     * @throws Throwable
     * @see CacheMethod
     */
    @Around("@annotation(com.d2c.cache.redis.annotation.CacheMethod)")
    public Object cacheMethod(ProceedingJoinPoint point) throws Throwable {
        Method md = getMethod(point);
        CacheMethod ann = md.getAnnotation(CacheMethod.class);
        String key = cacheKey(AnnUt.getValue(ann.value(), ann.key()), point, ann.token());
        ;
        Object value = getValue(ann, key);
        if (value == null) {
            value = point.proceed(point.getArgs());
            setValue(ann, key, value);
        }
        return value;
    }

    /**
     * 设置cacheKey值
     */
    private String cacheKey(String key, ProceedingJoinPoint point, boolean token) {
        if (!StringUtils.isBlank(key)) {
            key = spelKey(key, point);
        } else {
            key = cacheKey(point);
        }
        if (token) {
            String tk = getToken();
            if (tk != null) {
                key += ":" + tk;
            }
        }
        return key;
    }

    /**
     * 读取数据
     */
    private Object getValue(CacheMethod ann, String key) {
        Object value = null;
        switch (ann.type()) {
            case LOCAL:
                value = LocalCache.get(key);
                if (value != null) logger.debug("本地缓存命中... key： " + key + " value: " + value);
                break;
            case REDIS:
                value = cache.get(key);
                if (value != null) logger.debug("REDIS缓存命中... key： " + key + " value: " + value);
                break;
            default:
                value = LocalCache.get(key);
                if (value == null) {
                    value = cache.get(key);
                    if (value != null) {
                        LocalCache.setInMinutes(key, value, ann.min());
                        logger.debug("REDIS缓存命中, 并存入本地缓存... key： " + key + " value: " + value);
                    }
                } else {
                    logger.debug("本地缓存命中... key： " + key + " value: " + value);
                }
                break;
        }
        return value;
    }

    /**
     * 写入数据
     */
    private void setValue(CacheMethod ann, String key, Object value) {
        if (value == null) return;
        switch (ann.type()) {
            case LOCAL:
                LocalCache.setInMinutes(key, value, ann.min());
                logger.debug("缓存未命中, 加入本地缓存... key： " + key + " value:" + value + " min: " + ann.min());
                break;
            case REDIS:
                cache.setInMinutes(key, value, ann.min());
                logger.debug("缓存未命中, 加入REDIS缓存... key： " + key + " value:" + value + " min: " + ann.min());
                break;
            default:
                LocalCache.setInMinutes(key, value, ann.min());
                cache.setInMinutes(key, value, ann.min());
                logger.debug("缓存未命中, 加入本地缓存和REDIS缓存... key： " + key + " value:" + value + " min: " + ann.min());
                break;
        }
    }

    /**
     * SPEL解析KEY值
     */
    private String spelKey(String key, ProceedingJoinPoint point) {
        Method md = getMethod(point);
        Map<String, Object> map = new HashMap<>();
        DefaultParameterNameDiscoverer cov = new DefaultParameterNameDiscoverer();
        int i = 0;
        Object[] args = point.getArgs();
        for (String str : cov.getParameterNames(md)) {
            map.put(str, args[i]);
            i++;
        }
        return SpelUt.parse(key, map, String.class);
    }

    /**
     * 默认cache的key值
     * <p> com.d2c.behavior.provider.services.EventQueryServiceImpl.findProductVisit(Long,PageModel):3031125,PageBean[page-1, size-20, sort-null]
     */
    private String cacheKey(ProceedingJoinPoint joinPoint) {
        String key = cacheKeyMethod(joinPoint);
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            key += ":" + StringUt.join(args, ",");
        }
        logger.debug("设置cache的key值:" + key);
        return key;
    }

    /**
     * 缓存key
     * <p> DEMO: com.d2c.behavior.provider.services.EventQueryServiceImpl.findProductVisit(Long,PageModel)
     */
    private String cacheKeyMethod(ProceedingJoinPoint joinPoint) {
        Method md = getMethod(joinPoint);
        String tkey = "";
        for (Class<?> type : md.getParameterTypes()) {
            if (StringUtils.isNotBlank(tkey)) {
                tkey += ",";
            }
            tkey = tkey + type.getSimpleName();
        }
        return getClass(joinPoint).getName() + "." + md.getName() + "(" + tkey + ")";
    }

    private String getToken() {
        try {
            return getRequest().getHeader("accesstoken");
        } catch (Exception e) {
            return null;
        }
    }

}
