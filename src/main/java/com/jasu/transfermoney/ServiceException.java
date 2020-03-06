package com.jasu.transfermoney;

/*****************************************
 * @author hjs
 * @date 2020-02-28 19:47
 *****************************************/
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
