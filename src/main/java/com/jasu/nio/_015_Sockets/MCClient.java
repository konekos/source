package com.jasu.nio._015_Sockets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * @author @Jasu
 * @date 2018-09-27 13:54
 */
public class MCClient {

    final static int port = 9999;

    public static void main(String[] args) {
        try {
            MulticastSocket mcs = new MulticastSocket(port);
            InetAddress group = InetAddress.getByName("231.0.0.1");
            mcs.joinGroup(group);
            for (int i = 0; i < 10; i++) {
                byte[] buffer = new byte[256];
                DatagramPacket dgp = new DatagramPacket(buffer, buffer.length);
                mcs.receive(dgp);
                byte[] buffer2 = new byte[dgp.getLength()];
                System.arraycopy(dgp.getData(), 0, buffer2, 0, dgp.getLength());
                System.out.println(new String(buffer2));
            }
            mcs.leaveGroup(group);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
