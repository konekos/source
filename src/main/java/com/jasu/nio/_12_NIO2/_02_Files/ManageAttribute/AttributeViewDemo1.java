package com.jasu.nio._12_NIO2._02_Files.ManageAttribute;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;

/**
 * @author @Jasu
 * @date 2018-08-29 16:47
 */
public class AttributeViewDemo1 {
    public static void main(String[] args) {
        System.out.printf("Supports basic: %b%n",
                isSupported(BasicFileAttributeView.class));
        System.out.printf("Supports posix: %b%n",
                isSupported(PosixFileAttributeView.class));
        System.out.printf("Supports acl: %b%n",
                isSupported(AclFileAttributeView.class));
    }

    static boolean isSupported(Class<? extends FileAttributeView> clazz)
    {
        return Files.getFileAttributeView(Paths.get("."), clazz) != null;
    }
}
