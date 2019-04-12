package com.jasu.nio._07_Channels;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author @Jasu
 * @date 2018-08-09 17:36
 */
public class ChannelDemo6 {

    final static int BUFSIZE = 10;
    final static int LIMIT = 3;

    public static void main(String[] args) throws IOException {
        final Pipe pipe = Pipe.open();

        new Thread(() -> {
            WritableByteChannel src = pipe.sink();
            ByteBuffer buffer = ByteBuffer.allocate(BUFSIZE);
            for (int i = 0; i < LIMIT; i++) {
                buffer.clear();
                for (int j = 0; j < BUFSIZE; j++) {
                    buffer.put((byte) (Math.random() * 256));
                }
                buffer.flip();
                try {
                    while (src.write(buffer) > 0) ;
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                }
            }
            try {
                src.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }, "sender").start();

        new Thread(() -> {
            ReadableByteChannel dst = pipe.source();
            ByteBuffer buffer = ByteBuffer.allocate(BUFSIZE);

            try {
                while (dst.read(buffer) >= 0) {
                    buffer.flip();
                    while (buffer.remaining() > 0) {
                        System.out.println(buffer.get() & 255);
                    }
                    buffer.clear();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }, "receiver").start();
    }
}
