package com.jasu.nio._12_NIO2._02_Files;

import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

/**
 * @author @Jasu
 * @date 2018-08-28 17:51
 */
public class FileStoresDemo {
    public static void main(String[] args) {
        FileSystem fsDefault = FileSystems.getDefault();
        for (FileStore fileStore : fsDefault.getFileStores()) {
            System.out.printf("Filestore: %s%n", fileStore);
        }
    }
}
