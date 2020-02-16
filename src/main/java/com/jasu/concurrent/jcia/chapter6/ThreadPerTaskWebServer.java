package com.jasu.concurrent.jcia.chapter6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-02-16 18:19
 *****************************************/
public class ThreadPerTaskWebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        for (; ; ) {
            final Socket connection = socket.accept();
            new Thread(()->handleRequest(connection)).start();
        }
    }

    private static void handleRequest(Socket connection) {
        // handle
    }
}
