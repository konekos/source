package com.jasu.nio._015_Sockets;



import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * @author @Jasu
 * @date 2018-09-27 11:06
 */
public class DGClient {
    final static int PORT = 10001;
    final static String ADDR = "localhost";
    public static void main(String[] args) throws SocketException {
        System.out.println("client is starting");
        DatagramSocket dgs = new DatagramSocket();

        try {
            byte[] buffer;
            buffer = "Send me a datagram".getBytes();

            InetAddress ia = InetAddress.getByName(ADDR);
            DatagramPacket dgp = new DatagramPacket(buffer, buffer.length, InetAddress.getLocalHost(), PORT);
            dgs.send(dgp);

            byte[] buffer2 = new byte[100];
            dgp = new DatagramPacket(buffer2, buffer.length, InetAddress.getLocalHost(), PORT);
            dgs.receive(dgp);
            System.out.println(new String(buffer2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
