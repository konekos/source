package com.jasu.nio._13_AIO;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;

/**
 * @author @Jasu
 * @date 2018-09-14 18:09
 */
public class AsyncFileChannelDemo1 {
    public static void main(String[] args) throws IOException {
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get("page.html"));
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Thread ct = Thread.currentThread();
        channel.read(buffer, 0, null, new CompletionHandler<Integer, Void>() {

            @Override
            public void completed(Integer result, Void attachment) {
                System.out.println("Byte read: " + result);
                ct.interrupt();
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                System.out.println("failed: " + exc.toString());
                ct.interrupt();
            }
        });
        System.out.println("waiting for completion");
        try {
            ct.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        channel.close();
    }
}
