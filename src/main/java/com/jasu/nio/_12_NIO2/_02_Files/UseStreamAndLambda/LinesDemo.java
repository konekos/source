package com.jasu.nio._12_NIO2._02_Files.UseStreamAndLambda;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author @Jasu
 * @date 2018-09-12 18:12
 */
public class LinesDemo {
    public static void main(String[] args) throws IOException {

        Stream<String> lines = Files.lines(Paths.get("E:\\bocai/pom.xml"));
        System.out.println(lines.count());

        Files.list(Paths.get("C:/")).forEach(System.out::println);

        Files.walk(Paths.get("D:/Bin"), 2).forEach(System.out::println);
    }
}
