package com.jasu.nio._12_NIO2._01_BetterFileClass;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

/**
 * @author @Jasu
 * @date 2018-08-28 14:31
 */
public class NormalizationDemo {
    public static void main(String[] args) {
        Path path1 = Paths.get("reports", ".", "2015", "jan");
        System.out.println(path1);
        System.out.println(path1.normalize());

        path1 = Paths.get("reports", "2015", "..", "jan");
        System.out.println(path1);
        System.out.println(path1.normalize());
        System.out.println();

        path1 = Paths.get("reports", "2015", "jan");
        System.out.println(path1);
        System.out.println(path1.relativize(Paths.get("reports", "2016", "mar")));

        Iterator<Path> iterator = FileSystems.getDefault().getRootDirectories().iterator();
        Path root = iterator.next();

        try {
            if (root != null) {
                System.out.printf("Root: %s%n", root.toString());
                Path path = Paths.get(root.toString(), "reports", "2016", "mar");
                System.out.printf("Path: %s%n", path);
                System.out.println(path1.relativize(path));
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        System.out.println();
        path1 = Paths.get("report", "2015");
        System.out.println(path1);
        System.out.println(path1.resolve("apr"));
        System.out.println();
        Path path2 = Paths.get("reports", "2015", "jan");
        System.out.println(path2);
        System.out.println(path2.getParent());
        System.out.println(path2.resolveSibling(Paths.get("mar")));
        System.out.println(path2.resolve(Paths.get("mar")));
    }
}
