package com.jasu.nio._08_Selectors;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author @Jasu
 * @date 2018-08-10 17:34
 */
public class SelectorClient {

    private final static int DEFAULT_PORT = 9999;
    private static ByteBuffer bb = ByteBuffer.allocateDirect(1024);

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        try {
            SocketChannel sc = SocketChannel.open();
            InetSocketAddress addr = new InetSocketAddress("localhost", port);
            sc.connect(addr);

            while (sc.read(bb) != -1) {
                bb.flip();
            }
            Charset charset = StandardCharsets.UTF_8;
            System.out.println(charset.decode(bb).toString());
            bb.clear();
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
