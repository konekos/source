package com.jasu.concurrent.jcia.chapter7;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/*****************************************
 * @author hjs
 * @date 2020-02-17 23:46
 *****************************************/
public class ReaderThread extends Thread {
    private final Socket socket;
    private final InputStream in;

    public ReaderThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
    }

    @Override
    public void interrupt() {
        try {
            socket.close();
        } catch (IOException ignored) {

        }finally {
            super.interrupt();
        }
    }

    @Override
    public void run() {
        try {
            byte[] buf = new byte[1024];
            while (true) {
                int count = in.read(buf);
                if (count < 0) {
                    break;
                } else if (count > 0) {
                    processBuffer(buf, count);
                }
            }
        } catch (IOException e) {
            // 允许线程退出
        }
    }

    private void processBuffer(byte[] buf, int count) {

    }
}
