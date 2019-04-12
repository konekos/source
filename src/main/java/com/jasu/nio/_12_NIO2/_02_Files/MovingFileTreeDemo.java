package com.jasu.nio._12_NIO2._02_Files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author @Jasu
 * @date 2018-09-12 17:25
 */
public class MovingFileTreeDemo {
    public static void main(String[] args) throws IOException {
        Files.walkFileTree(Paths.get("F:\\store"), new MoveVisitor(Paths.get("F:\\store"), Paths.get("F:\\storage")));
    }

    static class MoveVisitor extends SimpleFileVisitor<Path> {
        private Path from, to;

        public MoveVisitor(Path from, Path to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            Files.copy(dir, to.resolve(from.relativize(dir)));
            return super.preVisitDirectory(dir, attrs);
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.move(file, to.resolve(from.relativize(file)));
            return super.visitFile(file, attrs);
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            return super.postVisitDirectory(dir, exc);
        }
    }
}
