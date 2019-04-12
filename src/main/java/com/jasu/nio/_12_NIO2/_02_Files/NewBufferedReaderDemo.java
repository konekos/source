package com.jasu.nio._12_NIO2._02_Files;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author @Jasu
 * @date 2018-09-03 16:34
 */
public class NewBufferedReaderDemo {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get("E:\\SpringSourceCode\\src\\main\\java\\com\\jasu\\nio\\_12_NIO2\\_02_Files\\NewBufferedReaderDemo.java"));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}
