package com.jasu.nio._07_Channels;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author @Jasu
 * @date 2018-08-03 17:14
 */
public class ChannelDemo2 {
    public static void main(String[] args) throws IOException {
        RandomAccessFile raf = new RandomAccessFile("temp", "rw");
        FileChannel fc = raf.getChannel();
        long pos;
        System.out.println(pos = fc.position());
        System.out.println(fc.size());

        String msg = "er34 we3e 34awe";
        ByteBuffer buffer = ByteBuffer.allocateDirect(msg.length()*2);
        buffer.asCharBuffer().put(msg);

        fc.write(buffer);
        fc.force(true);
        System.out.println("————————————————————————————————————");
        System.out.println(fc.position());
        System.out.println(fc.size());
        buffer.clear();
        fc.position(pos);
        fc.read(buffer);
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.print(buffer.getChar());
        }
    }
}
