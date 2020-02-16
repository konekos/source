package com.jasu.concurrent.jcia.chapter6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-02-16 18:19
 *****************************************/
public class TaskExecutionWebServer {

    private final static Executor exec = Executors.newFixedThreadPool(100);

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        for (; ; ) {
            final Socket connection = socket.accept();
            exec.execute(()->handleRequest(connection));
        }
    }

    private static void handleRequest(Socket connection) {
        // handle
    }
}
