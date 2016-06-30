package com.bisoncao.bcviewfinder;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @created 3:57 PM 01/26/2016
 * @author Bison Cao
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface ViewFinderBind {

    String DEFAULT_GROUP = "";

    /**
     * View ID to which the field will be bound.
     */
    int value() default 0;

    /**
     * Group identifier (you can bind different views with different groups)
     *
     * @return
     */
    String group() default DEFAULT_GROUP;
}
