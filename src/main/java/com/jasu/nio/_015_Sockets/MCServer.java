package com.jasu.nio._015_Sockets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * @author @Jasu
 * @date 2018-09-27 13:49
 */
public class MCServer {
    final static int port = 9999;

    public static void main(String[] args) {
        try {
            MulticastSocket multicastSocket = new MulticastSocket();
            InetAddress group = InetAddress.getByName("231.0.0.1");
            byte[] dummy = new byte[0];
            DatagramPacket dp = new DatagramPacket(dummy, 0, group, port);
            int i = 0;
            while (true) {
                byte[] buffer = ("line " + i).getBytes();
                dp.setData(buffer);
                dp.setLength(buffer.length);
                multicastSocket.send(dp);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
