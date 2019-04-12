package com.jasu.concurrent.lock;

import java.util.concurrent.CountDownLatch;

class DriverCountdownLatch {
   static class Driver { // ...
        public static void main(String[] args) throws InterruptedException {
            CountDownLatch startSignal = new CountDownLatch(2);
            CountDownLatch doneSignal = new CountDownLatch(10);

            for (int i = 0; i < 10; ++i) // create and start threads
                new Thread(new Worker(startSignal, doneSignal)).start();

            // 这边插入一些代码，确保上面的每个线程先启动起来，才执行下面的代码。
//            doSomethingElse();            // don't let run yet
            Thread.sleep(1000);
            // 因为这里 N == 1，所以，只要调用一次，那么所有的 await 方法都可以通过
            startSignal.countDown();      // let all threads proceed
            startSignal.countDown();      // let all threads proceed
//            doSomethingElse();
            Thread.sleep(1000);
            // 等待所有任务结束
            doneSignal.await();           // wait for all to finish
        }
        }

   static class Worker implements Runnable {
        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;

        Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        public void run() {
            try {
                // 为了让所有线程同时开始任务，我们让所有线程先阻塞在这里
                // 等大家都准备好了，再打开这个门栓
                startSignal.await();
                doWork();
                doneSignal.countDown();
            } catch (InterruptedException ex) {
            } // return;
        }

        void doWork() {
            System.out.println(Thread.currentThread().getId());
        }
    }
}
