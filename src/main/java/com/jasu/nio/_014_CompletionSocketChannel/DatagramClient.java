package com.jasu.nio._014_CompletionSocketChannel;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.MembershipKey;

/**
 * @author @Jasu
 * @date 2018-09-26 10:23
 */
public class DatagramClient {
    final static int PORT = 9999;

    public static void main(String[] args) throws IOException {
        NetworkInterface ni;
        ni = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
        DatagramChannel dc;
        dc = DatagramChannel.open(StandardProtocolFamily.INET)
                .setOption(StandardSocketOptions.SO_REUSEADDR, true).bind(new InetSocketAddress(PORT))
                .setOption(StandardSocketOptions.IP_MULTICAST_IF, ni);
        InetAddress group = InetAddress.getByName("239.255.0.1");
        MembershipKey membershipKey = dc.join(group, ni);

        ByteBuffer response = ByteBuffer.allocate(50);
        while (true) {
            dc.receive(response);
            response.flip();
            while (response.hasRemaining()) {
                System.out.print((char) response.get());
            }
            System.out.println();
            response.clear();
        }
    }
}
