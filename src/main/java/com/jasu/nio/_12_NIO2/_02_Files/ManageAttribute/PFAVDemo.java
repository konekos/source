package com.jasu.nio._12_NIO2._02_Files.ManageAttribute;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;

/**
 * @author @Jasu
 * @date 2018-08-31 14:03
 */
public class PFAVDemo {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("E:\\SpringSourceCode\\src\\main\\java\\com\\jasu\\nio\\_12_NIO2\\_02_Files\\PFAVDemo.java");
        PosixFileAttributes pfa;
        pfa = Files.readAttributes(path, PosixFileAttributes.class);
        System.out.printf("Group: %s%n", pfa.group());
        for (PosixFilePermission perm : pfa.permissions()) {
            System.out.printf("Permission: %s%n", perm);
        }
    }
}
