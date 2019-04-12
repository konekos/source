package com.jasu.spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.stereotype.Component;

/**
 * @author @Jasu
 * @date 2018-07-11 16:56
 */
@Aspect
@Component
public class ConcurrentOperationExecutor implements Ordered {
    private static final int DEFAULT_MAX_RETRIES = 2;
    private static final int DEFAULT_MAX_ORDER = 100;
    private int maxRetries;
    private int order = 1;

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public ConcurrentOperationExecutor() {
        this.maxRetries = DEFAULT_MAX_RETRIES;
        this.order = DEFAULT_MAX_ORDER;
    }


    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return this.order;
    }
    @Around("com.jasu.spring.annotation.SystemArchitecture.businessService() &&" + "@annotation(com.jasu.spring.annotation.Idempotent)")
    public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable {
        int numAttempts = 0;
        PessimisticLockingFailureException lockFailureException;
        do {
            numAttempts++;
            try {
                return pjp.proceed();
            } catch (PessimisticLockingFailureException ex) {
                lockFailureException = ex;
            }
        } while (numAttempts <= this.maxRetries);
        throw lockFailureException;
    }
}
