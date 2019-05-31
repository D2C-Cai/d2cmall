package com.d2c.common.api.annotation.search;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
public @interface SearchParam {

    String name() default "default";

    String value() default "";

    String[] values() default {};

}
