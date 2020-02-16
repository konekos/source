package com.jasu.concurrent.jcia.chapter6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-02-16 18:19
 *****************************************/
public class SingleThreadWebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        for (; ; ) {
            Socket connection = socket.accept();
            handleRequest(connection);
        }
    }

    private static void handleRequest(Socket connection) {
        // handle
    }
}
