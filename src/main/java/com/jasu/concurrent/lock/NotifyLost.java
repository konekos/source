package com.jasu.concurrent.lock;

/**
 * @author @Jasu
 * @date 2019-01-14 18:08
 */
public class NotifyLost {
    public static void main(String[] args) {
        Calculator c = new Calculator();
        new ReaderResult(c).start();
        c.start();
//        new ReaderResult(c).start(); //c先启动会通知丢失
    }
}
