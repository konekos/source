package com.jasu.nio._015_Sockets;

import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * @author @Jasu
 * @date 2018-09-26 17:28
 */
public class EchoClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket(Inet4Address.getLocalHost(), 34343);
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(os);
            PrintWriter pw = new PrintWriter(writer);
            pw.println("66666");
            pw.flush();
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            System.out.println(br.readLine());
            TimeUnit.SECONDS.sleep(50);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
