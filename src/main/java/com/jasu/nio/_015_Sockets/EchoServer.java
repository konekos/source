package com.jasu.nio._015_Sockets;

import java.io.*;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * @author @Jasu
 * @date 2018-09-26 17:37
 */
public class EchoServer {
    public static void main(String[] args) throws IOException {
        System.out.println("Starting echo server...");


        ServerSocket ss = new ServerSocket(34343, 2, Inet4Address.getLocalHost());
        while (true) {
            Socket s = ss.accept();

            try {
                InputStream is = s.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String msg = br.readLine();
                System.out.println(msg);
                OutputStream os = s.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                PrintWriter writer = new PrintWriter(osw);
                writer.println(msg);
                writer.flush();
                TimeUnit.SECONDS.sleep(60);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    s.close();
                } catch (IOException e) {
                    assert false;//should not happen in this context
                }
            }
        }
    }
}
