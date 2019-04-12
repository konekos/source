package com.jasu.netty.chapter04;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author @Jasu
 * @date 2018-09-29 15:43
 */
public class PlainOioServer {
    public void server(int port) throws IOException {
        //绑定
        final ServerSocket socket = new ServerSocket(port);

        try {
            //noinspection InfiniteLoopStatement
            for (; ; ) {
                //接收连接
                final Socket clientSocket = socket.accept();
                System.out.println("Accepted connection from " + clientSocket);

                new Thread(() -> {
                    OutputStream out;
                    try {
                        out = clientSocket.getOutputStream();
                        out.write("Hi\r\n".getBytes(Charset.forName("UTF-8")));
                        out.flush();
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            //ignore on close
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
