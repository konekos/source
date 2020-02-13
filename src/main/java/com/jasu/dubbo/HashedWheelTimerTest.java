package com.jasu.dubbo;

import org.apache.dubbo.common.timer.HashedWheelTimer;
import org.apache.dubbo.common.timer.Timeout;
import org.apache.dubbo.common.timer.TimerTask;

import java.util.concurrent.TimeUnit;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-01-17 17:58
 *****************************************/
public class HashedWheelTimerTest {
    public static void main(String[] args) {


        HashedWheelTimer timer = new HashedWheelTimer();
        for (int i = 0; i < 1000; i++) {
            timer.newTimeout(new TimerTask() {
                @Override
                public void run(Timeout timeout) throws Exception {
                    byte[] bytes1 = new byte[300 * 1024 * 1024];
                    byte[] bytes2 = new byte[200 * 1024 * 1024];
                    System.out.println(bytes1.length + bytes2.length);

                    TimeUnit.SECONDS.sleep(2);
                }
            }, 1, TimeUnit.SECONDS);
            timer.start();

            System.out.println("non blocking");
        }


    }
}
