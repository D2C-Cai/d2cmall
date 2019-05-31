package com.d2c.common.api.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.d2c.common.api.annotation.AssertEnum.NOT_NULL;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验注解, 默认是否为空
 *
 * @author wull
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface AssertColumn {

    AssertEnum type() default NOT_NULL;

    String value() default "";

    boolean nullable() default false;

    int min() default -1;

    int mineq() default -1;

    int max() default -1;

    int maxeq() default -1;

}
