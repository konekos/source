package com.jasu.nio._06_Buffers;

import org.junit.Test;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author @Jasu
 * @date 2018-08-01 16:35
 */
public class BuffersTest {

    @Test
    public void testBuffer() {
        Buffer buffer = ByteBuffer.allocate(7);
        System.out.println(buffer.capacity());
        System.out.println(buffer.limit());
        System.out.println(buffer.position());
        System.out.println(buffer.remaining());

        System.out.println("——————————————————————————");

        buffer.limit(5);
        System.out.println(buffer.capacity());
        System.out.println(buffer.limit());
        System.out.println(buffer.position());
        System.out.println(buffer.remaining());

        System.out.println("——————————————————————————————————————");
        buffer.position(3);
        System.out.println(buffer.capacity());
        System.out.println(buffer.limit());
        System.out.println(buffer.position());
        System.out.println(buffer.remaining());
        System.out.println(buffer);
    }

    @Test
    public void testCreateBuffer() {
        byte[] bytes = new byte[1024];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer = ByteBuffer.wrap(bytes, 10, 50);

    }

    @Test
    public void testBufferWrapping() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        if (buffer.hasArray()) {
            System.out.println(buffer.array());
            System.out.println(buffer.arrayOffset());
            System.out.println(buffer.capacity());
            System.out.println(buffer.limit());
            System.out.println(buffer.position());
            System.out.println(buffer.remaining());
        }

        byte[] bytes = new byte[200];
        ByteBuffer buffer1 = ByteBuffer.wrap(bytes);
        buffer1 = ByteBuffer.wrap(bytes, 10, 50);
        if (buffer1.hasArray()) {
            System.out.println(buffer1.array());
            System.out.println(buffer1.arrayOffset());
            System.out.println(buffer1.capacity());
            System.out.println(buffer1.limit());
            System.out.println(buffer1.position());
            System.out.println(buffer1.remaining());
        }
    }

    @Test
    public void testBufferWritingAndReading() {
        ByteBuffer buffer = ByteBuffer.allocate(7);

        buffer.put((byte) 10).put((byte) 20).put((byte) 30);

        System.out.println(buffer.capacity());
        System.out.println(buffer.limit());
        System.out.println(buffer.position());
        System.out.println(buffer.remaining());

        for (int i = 0; i < buffer.position(); i++) {
            System.out.println(buffer.get(i));
        }
    }

    @Test
    public void testFlip() {
        String[] poem =
                {
                        "Roses are red",
                        "Violets are blue",
                        "Sugar is sweet",
                        "And so are you."
                };
        CharBuffer buffer = CharBuffer.allocate(50);
        for (int i = 0; i < poem.length; i++) {
            // fill buffer
            for (int j = 0; j < poem[i].length(); j++) {
                buffer.put(poem[i].charAt(j));
            }
            //flip
            buffer.flip();
            //drain the buffer
            while (buffer.hasRemaining()) {
                System.out.print(buffer.get());
            }
            //empty the buffer
            buffer.clear();
            System.out.println();
        }
    }

    @Test
    public void testMark() {
        ByteBuffer buffer = ByteBuffer.allocate(7);
        buffer.put((byte) 10).put((byte) 20).put((byte) 30).put((byte) 40);
        buffer.limit(4);
        buffer.position(1).mark().position(3);
        System.out.println(buffer.get());
        buffer.reset();
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }

    @Test
    public void testUseChannelCopyFile() throws IOException {
        
    }


}
