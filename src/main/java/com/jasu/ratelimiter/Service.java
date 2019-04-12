package com.jasu.ratelimiter;

/**
 * @author @Jasu
 * @date 2018-09-07 11:41
 */
@org.springframework.stereotype.Service
public class Service {

    public void addMail(Integer integer) {
        Ses.addMail(integer);
    }
}
