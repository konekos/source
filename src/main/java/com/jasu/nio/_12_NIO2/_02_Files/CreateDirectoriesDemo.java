package com.jasu.nio._12_NIO2._02_Files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author @Jasu
 * @date 2018-09-04 17:05
 */
public class CreateDirectoriesDemo {
    public static void main(String[] args) throws IOException {

        Files.createDirectories(Paths.get("C:/test/test/test"));

        Path path = Files.createTempDirectory(Paths.get("C:/test"), "test");

        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
}
