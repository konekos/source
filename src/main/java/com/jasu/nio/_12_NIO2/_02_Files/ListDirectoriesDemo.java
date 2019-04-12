package com.jasu.nio._12_NIO2._02_Files;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author @Jasu
 * @date 2018-09-04 18:13
 */
public class ListDirectoriesDemo {
    public static void main(String[] args) throws IOException {

        Path path = Paths.get("C:/windows");
        DirectoryStream<Path> paths = Files.newDirectoryStream(path, p->p.getFileName().toString().endsWith("exe"));
        for (Path path1 : paths) {
            System.out.println(path1);
        }
        try (DirectoryStream<Path> ps = Files.newDirectoryStream(path, "*.exe")) {
            for (Path p : ps) {
                System.out.println(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
