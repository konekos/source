package com.jasu.nio._13_AIO;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author @Jasu
 * @date 2018-09-14 18:09
 */
public class AsyncFileChannelDemo {
    public static void main(String[] args) throws InterruptedException, IOException, ExecutionException {

        AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get("D:/cwbrepo.zip"));

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Future<Integer> result = channel.read(buffer, 0);
        while (!result.isDone()) {
            System.out.println("waiting");
            TimeUnit.MILLISECONDS.sleep(1);
        }
        System.out.println("Finished = " + result.isDone());
        System.out.println("Bytes read = " + result.get());
        channel.close();
    }
}
