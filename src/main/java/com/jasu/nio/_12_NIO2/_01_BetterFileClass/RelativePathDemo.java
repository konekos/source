package com.jasu.nio._12_NIO2._01_BetterFileClass;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * @author @Jasu
 * @date 2018-08-17 18:13
 */
public class RelativePathDemo {
    public static void main(String[] args) {

        FileSystem aDefault = FileSystems.getDefault();
        Path path = aDefault.getPath("a", "b", "c");
        System.out.println(path + " , " + path.isAbsolute());
        System.out.println(path.getRoot());

        Iterable<Path> rootDirectories = aDefault.getRootDirectories();

        for (Path root : rootDirectories) {
            path = aDefault.getPath(root.toString(), "a", "b", "c");

            System.out.println(path + " , " + path.isAbsolute());
            System.out.println(path.getRoot());

        }
    }
}
