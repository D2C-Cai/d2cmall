package com.d2c.cache.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 缓存方法返回数据
 *
 * @author wull
 */
@Target({METHOD})
@Retention(RUNTIME)
@Documented
public @interface CacheMethod {

    String value() default "";

    String key() default "";

    long min() default 30;

    /**
     * 是否尝试获取token, 加入到缓存key中
     */
    boolean token() default false;

    CacheType type() default CacheType.LOCAL_REDIS;

}
