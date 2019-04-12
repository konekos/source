package com.jasu.nio._04_Streams;

import org.junit.Test;

import java.io.*;

/**
 * @author @Jasu
 * @date 2018-07-31 16:03
 */
public class StreamsTest {
    @Test
    public void testBufferedOutputStream() throws IOException {
        FileOutputStream fos = new FileOutputStream("E:\\SpringSourceCode\\src\\main\\resources\\image.txt");
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bos.write(-1);
        bos.close();
    }

    @Test
    public void testBufferedInputStream() throws IOException {
        FileInputStream fis = new FileInputStream("E:\\SpringSourceCode\\src\\main\\resources\\image.txt");
        BufferedInputStream bis = new BufferedInputStream(fis);
        int read = bis.read();
        System.out.println(read);
    }

    @Test
    public void testDataInputAndOutputStream() throws IOException {
        try (FileOutputStream fos = new FileOutputStream("E:\\SpringSourceCode\\src\\main\\resources\\image.txt");DataOutputStream dos = new DataOutputStream(fos)) {
            dos.writeInt(999999999);
            dos.writeUTF("hehehwehwhewhewheh");
            dos.writeFloat(1.0F);
        }
        try (FileInputStream fis = new FileInputStream("E:\\SpringSourceCode\\src\\main\\resources\\image.txt"); DataInputStream dis = new DataInputStream(fis)) {
            System.out.println(dis.readInt());
            System.out.println(dis.readUTF());
            System.out.println(dis.readFloat());
        }
    }



}
