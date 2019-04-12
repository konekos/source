package com.jasu.nio._12_NIO2._02_Files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

/**
 * @author @Jasu
 * @date 2018-09-11 18:00
 */
public class CopyFileTreeDemo {
    public static void main(String[] args) throws IOException {
        Path source = Paths.get("D:/Bin");
        Path target = Paths.get("D:/BinCp");

        if (!Files.exists(source)) {
            System.err.printf("%s source path doesn't exist%n", source);
            return;
        }

        if (!Files.isDirectory(source)) // Is source a nondirectory?
        {
            if (Files.exists(target))
                if (Files.isDirectory(target)) // Is target a directory?
                    target = target.resolve(source.getFileName());
            try {
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                System.err.printf("I/O error: %s%n", ioe.getMessage());
            }
            return;
        }
        if (Files.exists(target) && !Files.isDirectory(target)) // Is target an existing  file?
        {
            System.err.printf("%s is not a directory%n", target);
            return;
        }

        EnumSet<FileVisitOption> options
                = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        CopyVisitor copier = new CopyVisitor(source, target);
        Files.walkFileTree(source, options, Integer.MAX_VALUE, copier);

    }

    public static class CopyVisitor extends SimpleFileVisitor<Path> {
        private Path fromPath;
        private Path toPath;

        private StandardCopyOption copyOption = StandardCopyOption.REPLACE_EXISTING;

        public CopyVisitor(Path fromPath, Path toPath) {
            this.fromPath = fromPath;
            this.toPath = toPath;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            System.out.println("dir = " + dir);
            System.out.println("fromPath = " + fromPath);
            System.out.println("toPath = " + toPath);
            System.out.println("fromPath.relativize(dir) = " +
                    fromPath.relativize(dir));
            System.out.println("toPath.resolve(fromPath.relativize(dir)) = " +
                    toPath.resolve(fromPath.relativize(dir)));
            Path targetPath = toPath.resolve(fromPath.relativize(dir));
            if (!Files.exists(targetPath))
                Files.createDirectory(targetPath);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            System.out.println("file = " + file);
            System.out.println("fromPath = " + fromPath);
            System.out.println("toPath = " + toPath);
            System.out.println("fromPath.relativize(file) = " +
                    fromPath.relativize(file));
            System.out.println("toPath.resolve(fromPath.relativize(file)) = " +
                    toPath.resolve(fromPath.relativize(file)));
            Files.copy(file, toPath.resolve(fromPath.relativize(file)),
                    copyOption);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            System.err.println(exc);
            return super.visitFileFailed(file, exc);
        }
    }
}
