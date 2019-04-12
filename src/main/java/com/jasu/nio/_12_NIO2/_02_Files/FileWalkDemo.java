package com.jasu.nio._12_NIO2._02_Files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author @Jasu
 * @date 2018-09-11 17:29
 */
public class FileWalkDemo {
    public static void main(String[] args) throws IOException {
        Files.walkFileTree(Paths.get("F:/storage"), new DoNothingFileVisitor());
    }

    static class DoNothingFileVisitor extends SimpleFileVisitor<Path> {
        /**
         * Initializes a new instance of this class.
         */
        protected DoNothingFileVisitor() {
            super();
        }

        /**
         * Invoked for a directory before entries in the directory are visited.
         *
         * <p> Unless overridden, this method returns {@link FileVisitResult#CONTINUE
         * CONTINUE}.
         *
         * @param dir
         * @param attrs
         */
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            System.out.printf("preVisitDirectory: %s%n", dir);

//            System.out.printf(" lastModifiedTime: %s%n",
//                    attrs.lastModifiedTime());
//            System.out.printf(" size: %d%n%n", attrs.size());
            return super.preVisitDirectory(dir, attrs);
        }

        /**
         * Invoked for a file in a directory.
         *
         * <p> Unless overridden, this method returns {@link FileVisitResult#CONTINUE
         * CONTINUE}.
         *
         * @param file
         * @param attrs
         */
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            System.out.printf("visitFile: %s%n%n", file);
//            System.out.printf(" lastModifiedTime: %s%n",
//                    attrs.lastModifiedTime());
//            System.out.printf(" size: %d%n%n", attrs.size());
            return super.visitFile(file, attrs);
        }

        /**
         * Invoked for a file that could not be visited.
         *
         * <p> Unless overridden, this method re-throws the I/O exception that prevented
         * the file from being visited.
         *
         * @param file
         * @param exc
         */
        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            System.out.printf("visitFileFailed: %s %s%n%n", file, exc);
            return super.visitFileFailed(file, exc);
        }

        /**
         * Invoked for a directory after entries in the directory, and all of their
         * descendants, have been visited.
         *
         * <p> Unless overridden, this method returns {@link FileVisitResult#CONTINUE
         * CONTINUE} if the directory iteration completes without an I/O exception;
         * otherwise this method re-throws the I/O exception that caused the iteration
         * of the directory to terminate prematurely.
         *
         * @param dir
         * @param exc
         */
        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            System.out.printf("postVisitDirectory: %s %s%n%n", dir, exc);
            return super.postVisitDirectory(dir, exc);
        }
    }

}
