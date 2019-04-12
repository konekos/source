package com.jasu.spring.annotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author @Jasu
 * @date 2018-07-11 17:47
 */
@Aspect
public class SystemArchitecture {
    @Pointcut("execution(* com.jasu.spring.service.*.*(..))")
    public void businessService() {}
}
