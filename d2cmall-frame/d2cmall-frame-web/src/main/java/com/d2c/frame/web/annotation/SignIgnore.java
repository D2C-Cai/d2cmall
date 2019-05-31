package com.d2c.frame.web.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 不进行签名验证
 *
 * @author wull
 */
@Target({METHOD})
@Retention(RUNTIME)
public @interface SignIgnore {

}
