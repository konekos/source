package com.jasu;

/**
 * @author @Jasu
 * @date 2019-04-11 18:10
 */
public class ThreadDemo implements Runnable{

    private int count;

    public ThreadDemo() {
        count = 0;
    }

    @Override
//循环打印
    public void run() {
        System.out.println("before syn");
        synchronized (this){
            for (int i = 0; i < 10; i++){
                try {
                    System.out.println(Thread.currentThread().getName() + ":" + (count++));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("after syn");
    }

}
