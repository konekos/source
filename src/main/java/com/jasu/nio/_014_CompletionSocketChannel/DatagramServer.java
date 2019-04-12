package com.jasu.nio._014_CompletionSocketChannel;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @author @Jasu
 * @date 2018-09-26 10:18
 */
public class DatagramServer {
    final static int PORT = 9999;

    public static void main(String[] args) throws IOException {
        NetworkInterface ni;
        ni = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
        DatagramChannel dc;
        dc = DatagramChannel.open(StandardProtocolFamily.INET)
                .setOption(StandardSocketOptions.SO_REUSEADDR, true)
                .bind(new InetSocketAddress(PORT)).setOption(StandardSocketOptions.IP_MULTICAST_IF, ni);

        InetAddress group = InetAddress.getByName("239.255.0.1");
        int i = 0;
        while (true)
        {
            ByteBuffer bb = ByteBuffer.wrap(("line " + i).getBytes());
            dc.send(bb, new InetSocketAddress(group, PORT));
            i++;
        }

    }
}
