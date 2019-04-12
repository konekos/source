package com.jasu.nio._13_AIO.AsynchronousSocketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;

/**
 * @author @Jasu
 * @date 2018-09-17 17:58
 */
public class Client {
    private final static Charset CSUTF8 = Charset.forName("UTF-8");
    private final static int PORT = 9090;
    private final static String HOST = "localhost";

    public static void main(String[] args) {

        AsynchronousSocketChannel channel;

        try {
            channel = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            System.err.println("Unable to open client socket channel");
            return;
        }

        try {
            channel.connect(new InetSocketAddress(HOST, PORT)).get();
            System.out.printf("Client at %s connected%n",
                    channel.getLocalAddress());
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Server not responding");
            return;
        }catch (IOException e) {
            System.err.println("Unable to obtain client socket channel's " +
                    "local address");
            return;
        }


        Attachment attachment = new Attachment();
        attachment.channel = channel;
        attachment.isReadMode = false;
        attachment.buffer = ByteBuffer.allocate(2048);
        attachment.mainThd = Thread.currentThread();

        byte[] data = "Hello".getBytes();
        attachment.buffer.put(data);
        attachment.buffer.flip();
        channel.write(attachment.buffer, attachment, new ReadWriteHandler());

        try {
            attachment.mainThd.join();
        } catch (InterruptedException e) {
            System.out.println("Client terminating");
        }
    }
}
