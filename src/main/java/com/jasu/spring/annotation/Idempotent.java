package com.jasu.spring.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author @Jasu
 * @date 2018-07-12 10:14
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
    //marker annotation
}
