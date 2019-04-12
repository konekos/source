package com.jasu.nio._12_NIO2._02_Files.UseStreamAndLambda;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author @Jasu
 * @date 2018-09-12 18:02
 */
public class FindDemo {
    public static void main(String[] args) throws IOException {

        Stream<Path> pathStream = Files.find(Paths.get("F:/storage"), 100, (path, basicFileAttributes) -> "logo.jpg".equals(path.getFileName().toString()));
        pathStream.forEach(System.out::println);
    }
}
