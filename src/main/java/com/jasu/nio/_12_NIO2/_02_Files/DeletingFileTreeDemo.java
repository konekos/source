package com.jasu.nio._12_NIO2._02_Files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author @Jasu
 * @date 2018-09-12 17:14
 */
public class DeletingFileTreeDemo {
    public static void main(String[] args) throws IOException {
        Path path = Files.walkFileTree(Paths.get("D:/BinCp"), new DeleteVisitor());

    }

    static class DeleteVisitor extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.deleteIfExists(file);
            return super.visitFile(file, attrs);
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.deleteIfExists(dir);
            return super.postVisitDirectory(dir, exc);
        }
    }

}
