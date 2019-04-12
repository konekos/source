package com.jasu.nio._07_Channels;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author @Jasu
 * @date 2018-08-08 17:03
 */
public class ChannelServer {
    public static void main(String[] args) throws IOException {

        System.out.println("starting server");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(9999));
        ssc.configureBlocking(false);
        String msg = "Local Address: " + ssc.socket().getLocalSocketAddress();
        System.out.println(msg);
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

        while (true) {
            System.out.print(".");
            SocketChannel sc = ssc.accept();
            if (sc != null) {
                System.out.println();
                System.out.println("Receive connection from " + sc.socket().getRemoteSocketAddress());
                buffer.rewind();
                sc.write(buffer);
                sc.close();
            } else {
                try{
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    //shouldn't happen
                    assert false;
                }
            }
        }
    }
}
