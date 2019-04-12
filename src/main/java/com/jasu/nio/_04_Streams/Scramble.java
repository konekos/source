package com.jasu.nio._04_Streams;

/**
 * @author @Jasu
 * @date 2018-07-30 16:30
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Scramble {
    public static void main(String[] args) {

        FileInputStream fis = null;
        ScrambledOutputStream sos = null;
        try {
            fis = new FileInputStream("E:\\SpringSourceCode\\src\\main\\resources\\image.txt");
            FileOutputStream fos = new FileOutputStream("E:\\SpringSourceCode\\src\\main\\resources\\imageCopy.txt");
            sos = new ScrambledOutputStream(fos, makeMap());
            int b;
            while ((b = fis.read()) != -1)
                sos.write(b);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            if (sos != null)
                try {
                    sos.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
        }
    }

    static int[] makeMap() {
        int[] map = new int[256];
        for (int i = 0; i < map.length; i++)
            map[i] = i;
        // Shuffle map.
        Random r = new Random(0);
        for (int i = 0; i < map.length; i++) {
            int n = r.nextInt(map.length);
            int temp = map[i];
            map[i] = map[n];
            map[n] = temp;
        }
        return map;
    }
}

