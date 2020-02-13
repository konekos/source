package com.jasu.concurrent.jcia.chapter3;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-02-12 1:01
 *****************************************/
public class NoVisibility {
    private static boolean ready;
    private static int num;

    private void s() {
        System.out.println(this);
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            while (!ready) {
                Thread.yield();
            }
            System.out.println(num);
        }).start();

        num = 42;
        ready = true;
        new NoVisibility().s();
    }
}
