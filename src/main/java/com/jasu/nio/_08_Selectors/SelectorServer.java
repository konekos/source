package com.jasu.nio._08_Selectors;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author @Jasu
 * @date 2018-08-10 16:52
 */
public class SelectorServer {

    final static int DEFAULT_PORT = 9999;
    static ByteBuffer bb = ByteBuffer.allocateDirect(8);

    public static void main(String[] args) throws IOException {
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        System.out.println("Server starting ... listening on port " + port);
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(port));
        ssc.configureBlocking(false);

        Selector s = Selector.open();
        ssc.register(s, SelectionKey.OP_ACCEPT);

        while (true) {
            int n = s.select();
            if (n == 0) {
                continue;
            }
            Iterator<SelectionKey> iterator = s.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    SocketChannel sc;
                    sc = ((ServerSocketChannel) key.channel()).accept();
                    if (sc == null) {
                        continue;
                    }
                    System.out.println("Receiving connection");
                    bb.clear();
                    bb.putLong(System.currentTimeMillis());
                    bb.flip();
                    System.out.println("Writing current time");
                    while (bb.hasRemaining()) {
                        sc.write(bb);
                    }
                    sc.close();
                }
                iterator.remove();
            }
        }
    }
}
