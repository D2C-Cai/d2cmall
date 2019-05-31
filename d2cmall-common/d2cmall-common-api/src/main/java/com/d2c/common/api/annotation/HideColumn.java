package com.d2c.common.api.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 隐藏字段明细
 *
 * @author wull
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface HideColumn {

    HideEnum type() default HideEnum.REALNAME;

    int start() default -1;

    int end() default -1;

}
