package com.jasu.nio._014_CompletionSocketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * @author @Jasu
 * @date 2018-09-25 15:06
 */
public class ChannelClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        Set<SocketOption<?>> socketOptions = sc.supportedOptions();
        for (SocketOption<?> socketOption : socketOptions) {
            System.out.println(socketOption);
        }
        System.out.println(sc.getOption(StandardSocketOptions.SO_RCVBUF));
        sc.configureBlocking(false);
        InetSocketAddress addr = new InetSocketAddress("localhost", 9999);
        sc.connect(addr);
        while (!sc.finishConnect()) {
            System.out.println("waiting to finish connection");
        }
        ByteBuffer buffer = ByteBuffer.allocate(200);
        while (sc.read(buffer) >= 0) {
            buffer.flip();
            while (buffer.hasRemaining())
                System.out.print((char) buffer.get());
            buffer.clear();
        }
        sc.close();
    }
}

