package com.jasu.nio._05_WriterAndReaders;

import org.junit.Test;

import java.io.*;

/**
 * @author @Jasu
 * @date 2018-08-01 14:39
 */
public class WriterAndReaderTest {
    private final static String MSG = "hhahahah 123 dsb";
    static String[] lines =
            {
                    "It was the best of times, it was the worst of times,",
                    "it was the age of wisdom, it was the age of foolishness,",
                    "it was the epoch of belief, it was the epoch of incredulity,",
                    "it was the season of Light, it was the season of Darkness,",
                    "it was the spring of hope, it was the winter of despair."
            };


    @Test
    public void testFileWriterReader() throws IOException {
        try (FileWriter writer = new FileWriter("E:\\SpringSourceCode\\src\\main\\resources\\image.txt")) {
            writer.write(MSG, 0, MSG.length());
        }
        char[] buffer = new char[MSG.length()];
        try (FileReader reader = new FileReader("E:\\SpringSourceCode\\src\\main\\resources\\image.txt")) {
            reader.read(buffer);
            System.out.println(buffer);
        }
    }

    @Test
    public void testBufferWriterAndReader() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("E:\\SpringSourceCode\\src\\main\\resources\\image.txt"))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("E:\\SpringSourceCode\\src\\main\\resources\\image.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
