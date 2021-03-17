package com.jasu.nio._01_IOBasicAndApis;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author @Jasu
 * @date 2018-07-23 18:25
 */
public class IOBasicsAndApis {

    @Test
    public void testRandomAccessFile() throws IOException {
        RandomAccessFile file = new RandomAccessFile("C:\\Users\\Judy\\Desktop\\命令.txt", "r");
        int empIndex = 10;
        long length = 2;
        file.seek(empIndex * length);
    }

    @Test
    public void testFileInputStream() throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("image.png");
            //read
            int _byte;
            while ((_byte = fis.read()) != -1) {
                //do
            }
        } catch (IOException e) {
            //handle
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    @Test
    public void testBufferedInputStream() {
        try (FileInputStream fis = new FileInputStream("image.png")) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSystemInOutError() throws IOException {
//        int ch = System.in.read();
        System.out.println("hello");
        System.out.println();
    }

    @Test
    public void testSystemProperties() {
        System.out.println(System.getProperty("user.dir"));
        System.out.println(System.getProperty("file.separator"));

    }

}
