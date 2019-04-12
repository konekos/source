package com.jasu.nettyinaction._01_Reactor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 很明显，为了避免资源耗尽，我们采用线程池的方式来处理读写服务。但是这么做依然有很明显的弊端：
 *
 * 1. 同步阻塞IO，读写阻塞，线程等待时间过长
 *
 * 2. 在制定线程策略的时候，只能根据CPU的数目来限定可用线程资源，不能根据连接并发数目来制定，也就是连接有限制。否则很难保证对客户端请求的高效和公平。
 *
 * 3. 多线程之间的上下文切换，造成线程使用效率并不高，并且不易扩展
 *
 * 4. 状态数据以及其他需要保持一致的数据，需要采用并发同步控制
 *
 * @author @Jasu
 * @date 2018-12-25 15:12
 */
public class BIOServer {
    ServerSocket serverSocket = new ServerSocket(34343, 2, InetAddress.getLocalHost());
    ExecutorService executorService = Executors.newFixedThreadPool(100);

    public BIOServer() throws IOException {
    }

    public static void main(String[] args) {
        try {
            BIOServer bioServer = new BIOServer();
            bioServer.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //主线程维护连接
    public void run() {
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                //提交线程池
                executorService.submit(new Handler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //处理读写服务
     class Handler implements Runnable {

         Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String readData = bufferedReader.readLine();
                while (readData != null) {
                    readData = bufferedReader.readLine();
                    System.out.println(readData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
