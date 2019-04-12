package com.jasu.spring.aspect;

import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author @Jasu
 * @date 2018-07-11 18:09
 */
@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler
    @ResponseBody
    public String errorHandler(PessimisticLockingFailureException ex) {
        return "error!";
    }
}
