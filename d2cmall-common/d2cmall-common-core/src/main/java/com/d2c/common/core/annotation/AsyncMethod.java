package com.d2c.common.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 异步方法
 *
 * @author wull
 */
@Target({METHOD})
@Retention(RUNTIME)
@Documented
public @interface AsyncMethod {

    long delay() default 0; // 延迟（单位：毫秒）

}
