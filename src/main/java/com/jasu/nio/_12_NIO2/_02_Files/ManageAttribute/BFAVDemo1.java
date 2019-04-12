package com.jasu.nio._12_NIO2._02_Files.ManageAttribute;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Instant;

/**
 * @author @Jasu
 * @date 2018-08-30 10:49
 */
public class BFAVDemo1 {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("E:\\SpringSourceCode\\src\\main\\java\\com\\jasu\\nio\\_12_NIO2\\_02_Files\\BFAVDemo1.java");

        System.out.printf("Creation time: %s%n",
                Files.getAttribute(path, "creationTime"));
        System.out.printf("File key: %s%n",
                Files.getAttribute(path, "fileKey"));
        System.out.printf("Is directory: %b%n",
                Files.getAttribute(path, "isDirectory"));
        System.out.printf("Is other: %b%n",
                Files.getAttribute(path, "isOther"));
        System.out.printf("Is regular file: %b%n",
                Files.getAttribute(path, "isRegularFile"));
        System.out.printf("Is symbolic link: %b%n",
                Files.getAttribute(path, "isSymbolicLink"));
        System.out.printf("Last access time: %s%n",
                Files.getAttribute(path, "lastAccessTime"));
        System.out.printf("Last modified time: %s%n",
                Files.getAttribute(path, "lastModifiedTime"));
        System.out.printf("Size: %d%n", Files.getAttribute(path, "size"));

        Files.setAttribute(path, "lastModifiedTime",
                FileTime.from(Instant.now().plusSeconds(60)));
        System.out.printf("Last modified time: %s%n",
                Files.getAttribute(path, "lastModifiedTime"));
    }
}
