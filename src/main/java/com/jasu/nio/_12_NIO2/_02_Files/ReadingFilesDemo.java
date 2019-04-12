package com.jasu.nio._12_NIO2._02_Files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author @Jasu
 * @date 2018-09-03 15:39
 */
public class ReadingFilesDemo {
    public static void main(String[] args) throws IOException {
        List<String> stringList = Files.readAllLines(Paths.get("E:\\SpringSourceCode\\src\\main\\java\\com\\jasu\\nio\\_12_NIO2\\_02_Files\\ReadingFilesDemo.java"));
        for (String s : stringList) {
            System.out.println(s);
        }
    }
}
