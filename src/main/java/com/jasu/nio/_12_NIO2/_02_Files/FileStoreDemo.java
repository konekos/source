package com.jasu.nio._12_NIO2._02_Files;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author @Jasu
 * @date 2018-08-28 17:45
 */
public class FileStoreDemo {

    public static void main(String[] args) throws IOException {
        FileStore fs = Files.getFileStore(Paths.get("."));
        System.out.printf("Total space: %d%n", fs.getTotalSpace());
        System.out.printf("Unallocated space: %d%n",
                fs.getUnallocatedSpace());
        System.out.printf("Usable space: %d%n",
                fs.getUsableSpace());
        System.out.printf("Read only: %b%n", fs.isReadOnly());
        System.out.printf("Name: %s%n", fs.name());
        System.out.printf("Type: %s%n%n", fs.type());
    }
}
