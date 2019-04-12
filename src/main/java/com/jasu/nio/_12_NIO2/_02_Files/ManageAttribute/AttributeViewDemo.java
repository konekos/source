package com.jasu.nio._12_NIO2._02_Files.ManageAttribute;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.Set;

/**
 * @author @Jasu
 * @date 2018-08-29 16:47
 */
public class AttributeViewDemo {
    public static void main(String[] args) {
        FileSystem fsDefault = FileSystems.getDefault();
        Set<String> set = fsDefault.supportedFileAttributeViews();
        System.out.println(set);
    }
}
