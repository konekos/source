package com.jasu.nio._12_NIO2._02_Files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author @Jasu
 * @date 2018-08-31 17:52
 */
public class FilesAndDirectoriesDemo {

    public static void main(String[] args) throws IOException {

        System.out.println(System.getProperty("java.io.tmpdir"));
    }

    static void pathsAndDirectories() {
        Path path1 = Paths.get("E:\\SpringSourceCode\\src\\main\\java\\com\\jasu\\nio\\_12_NIO2\\_02_Files\\FilesAndDirectoriesDemo.java");
        System.out.printf("Path1: %s%n", path1);
        System.out.printf("Exists: %b%n", Files.exists(path1));
        System.out.printf("Not exists: %b%n", Files.notExists(path1));
        System.out.printf("Is directory: %b%n", Files.isDirectory(path1));
        System.out.printf("Is executable: %b%n", Files.isExecutable(path1));

        try {
            System.out.printf("Hidden: %b%n", Files.isHidden(path1));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        System.out.printf("Is readable: %b%n", Files.isReadable(path1));
        System.out.printf("Is regular file: %b%n",
                Files.isRegularFile(path1));
        System.out.printf("Is writable: %b%n",
                Files.isWritable(path1));

        try {
            System.out.println("Is Same" + Files.isSameFile(path1, Paths.get("E:\\SpringSourceCode\\src\\main\\java\\com\\jasu\\nio\\_12_NIO2\\_02_Files\\FileStoreDemo.java")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void createFiles() throws IOException {
        Files.createFile(Paths.get("C:/a.jpg"));
    }

    static void createTempFiles() throws IOException {
        Path path = Files.createTempFile("test", null);
        path.toFile().deleteOnExit();
    }
}
