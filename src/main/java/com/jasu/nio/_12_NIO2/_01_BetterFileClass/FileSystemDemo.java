package com.jasu.nio._12_NIO2._01_BetterFileClass;

import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.util.List;

/**
 * @author @Jasu
 * @date 2018-08-17 11:17
 */
public class FileSystemDemo {

    public static void main(String[] args) throws URISyntaxException {
        FileSystem fileSystem = FileSystems.getDefault();
        System.out.println(fileSystem);
        fileSystem.getSeparator();

        List<FileSystemProvider> fileSystemProviders = FileSystemProvider.installedProviders();
        for (FileSystemProvider provider : fileSystemProviders) {
            System.out.println(provider);
        }

        Path path = Paths.get("");
        path.getFileSystem();


        Path path1 = fileSystem.getPath("x", "y");
        Path path2 = fileSystem.getPath("c:/x/y");
        System.out.println(path1.getFileName());
        System.out.println(path1.getName(1));
        System.out.println(path1.getNameCount());
        System.out.println(path1.getRoot());
        System.out.println(path1.subpath(0, 2));
        Paths.get("x", "y");


    }
}
