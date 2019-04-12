package com.jasu.nio._12_NIO2._01_BetterFileClass;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author @Jasu
 * @date 2018-08-17 18:19
 */
public class AbsolutePathDemo {
    public static void main(String[] args) {

        Path path = Paths.get("a", "b", "c");
        System.out.printf("Path: %s%n", path.toString());
        System.out.printf("Absolute: %b%n", path.isAbsolute());
        path = path.toAbsolutePath();
        System.out.printf("Path: %s%n", path.toString());
        System.out.printf("Absolute: %b%n", path.isAbsolute());
    }
}
