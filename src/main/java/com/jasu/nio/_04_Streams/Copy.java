package com.jasu.nio._04_Streams;

/**
 * @author @Jasu
 * @date 2018-07-30 15:35
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Copy {
    public static void main(String[] args) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream("E:\\SpringSourceCode\\src\\main\\resources\\image.txt");
            fos = new FileOutputStream("E:\\SpringSourceCode\\src\\main\\resources\\imageCopy.txt");
            int b; // I chose b instead of byte because byte is a reserved
            // word.
            while ((b = fis.read()) != -1) {
                fos.write(b);
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            System.err.println("I/O error: " + ioe.getMessage());
        } finally {
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException ioe) {
                    assert false; // shouldn't happen in this context
                }
            if (fos != null)
                try {
                    fos.close();
                } catch (IOException ioe) {
                    assert false; // shouldn't happen in this context
                }
        }
    }
}
