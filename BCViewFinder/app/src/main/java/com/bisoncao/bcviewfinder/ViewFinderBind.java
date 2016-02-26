package com.bisoncao.bcviewfinder;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Bison Cao
 * @created 15:57 01/26/2016
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
     * group identifier, so you can bind different view using different group
     *
     * @return
     */
    String group() default DEFAULT_GROUP;
}
