package com.jasu.nio._12_NIO2._02_Files.ManageAttribute;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author @Jasu
 * @date 2018-08-30 10:35
 */
public class BFAVDemo {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("E:\\SpringSourceCode\\src\\main\\java\\com\\jasu\\nio\\_12_NIO2\\_02_Files\\BFAVDemo.java");
        BasicFileAttributes bfa;
        bfa = Files.readAttributes(path, BasicFileAttributes.class);
        System.out.printf("Creation time: %s%n", bfa.creationTime());
        System.out.printf("File key: %s%n", bfa.fileKey());
        System.out.printf("Is directory: %b%n", bfa.isDirectory());
        System.out.printf("Is other: %b%n", bfa.isOther());
        System.out.printf("Is regular file: %b%n", bfa.isRegularFile());
        System.out.printf("Is symbolic link: %b%n", bfa.isSymbolicLink());
        System.out.printf("Last access time: %s%n", bfa.lastAccessTime());
        System.out.printf("Last modified time: %s%n", bfa.lastModifiedTime());
        System.out.printf("Size: %d%n", bfa.size());
    }
}
