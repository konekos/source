package com.jasu.nio._13_AIO.AsyncServerSocketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;

/**
 * @author @Jasu
 * @date 2018-09-17 16:23
 */
public class Server {
    private final static int PORT = 9090;
    private final static String HOST = "localhost";

    public static void main(String[] args) {
        AsynchronousServerSocketChannel channelServer;
        try {
            channelServer = AsynchronousServerSocketChannel.open();
            channelServer.bind(new InetSocketAddress(HOST, PORT));
            System.out.printf("Server listening at %s%n",
                    channelServer.getLocalAddress());
        } catch (IOException e) {
            System.err.println("Unable to open or bind server socket channel");
            return;
        }

        Attachment attachment = new Attachment();
        attachment.channelServer = channelServer;
        channelServer.accept(attachment, new ConnectionHandler());

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            System.out.println("Server terminating");
        }
    }
}
