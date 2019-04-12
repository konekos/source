package com.jasu.nio._07_Channels;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author @Jasu
 * @date 2018-08-09 11:47
 */
public class ChannelClient {

    public static void main(String[] args) throws IOException {

        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        InetSocketAddress addr = new InetSocketAddress("localhost", 9999);
        sc.connect(addr);
        while (!sc.finishConnect()) {
            System.out.println("waiting for connecting");
        }
        ByteBuffer buffer = ByteBuffer.allocate(200);
        while (sc.read(buffer) >= 0) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }
            buffer.clear();
        }
        sc.close();
    }
}
