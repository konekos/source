package com.jasu.concurrent.jcia.chapter10;

/*****************************************
 * @author hjs
 * @date 2020-02-20 1:28
 *****************************************/
public class LeftRightDeadLock {
    private final Object left = new Object();
    private final Object right = new Object();

    public void leftRight() {
        synchronized (left) {
            synchronized (right) {
                doSomething();
            }
        }
    }

    public void rightLeft() {
        synchronized (right) {
            synchronized (left) {
                doSomething();
            }
        }
    }

    private void doSomething() {


    }
}
