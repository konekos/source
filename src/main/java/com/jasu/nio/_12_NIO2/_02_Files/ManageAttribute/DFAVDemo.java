package com.jasu.nio._12_NIO2._02_Files.ManageAttribute;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributes;

/**
 * @author @Jasu
 * @date 2018-08-30 17:19
 */
public class DFAVDemo {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("E:\\SpringSourceCode\\src\\main\\java\\com\\jasu\\nio\\_12_NIO2\\_02_Files\\DFAVDemo.java");
        DosFileAttributes dfa;
        dfa = Files.readAttributes(path, DosFileAttributes.class);
        System.out.printf("Is archive: %b%n", dfa.isArchive());
        System.out.printf("Is hidden: %b%n", dfa.isHidden());
        System.out.printf("Is readonly: %b%n", dfa.isReadOnly());
        System.out.printf("Is system: %b%n", dfa.isSystem());
    }
}
