package com.jasu.nio._02_File;

import org.junit.Test;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author @Jasu
 * @date 2018-07-25 17:06
 */
public class FileTest
{

    @Test
    public void testStoredAbstractPaths() throws IOException {
        File file = new File("C:/json");
        System.out.println(file.getAbsoluteFile().getName());
        System.out.println(file.getAbsolutePath());
        System.out.println("————————————————————————");
        System.out.println(file.getPath());
        System.out.println("————————————————————————");
        System.out.println(file.getCanonicalFile());
        System.out.println(file.getCanonicalPath());
        System.out.println("————————————————————————");
        System.out.println(file.getParent());
        System.out.println(file.getParentFile());
        System.out.println(file.isDirectory());
        System.out.println(file.length());
    }

    @Test
    public void testListRoots() {
        File[] files = File.listRoots();
        for (File file : files) {
            System.out.println(file);
        }
    }

    @Test
    public void testFreeSpace() {
        File[] file = File.listRoots();
        for (File f : file) {
            System.out.println(f.getFreeSpace());
            System.out.println(f.getUsableSpace());
        }
    }

    @Test
    public void testListFiles() {
        File file = new File("C:/windows");
        for (String s : file.list((dir, name) -> name.length() > 5 && name.endsWith(".exe"))) {
            System.out.println(s);
        }
    }

    @Test
    public void testCreateModifyFiles() {
        File file = new File("C:/jss");
        boolean b = file.renameTo(new File("C:/json"));
        System.out.println(b);
    }

    @Test
    public void testTemporaryFile() throws IOException {
        System.out.println(System.getProperty("java.io.tmpdir"));
        File temp = File.createTempFile("text", ".txt");
        System.out.println(temp);
        temp.deleteOnExit();
    }

    @Test
    public void testPermission() {
        File file = new File("C:/json");
        System.out.println("Checking permissions for ");
        System.out.println(" Execute = " + file.canExecute());
        System.out.println(" Read = " + file.canRead());
        System.out.println(" Write = " + file.canWrite());
    }

    @Test
    public void testRWSync() throws IOException {
        RandomAccessFile raf = new RandomAccessFile("E:\\SpringSourceCode\\src\\main\\resources\\image.txt", "rw");
        FileDescriptor fd = raf.getFD();
        // Perform a critical write operation.
        raf.write(123);
        // Synchronize with the underlying disk by flushing the operating system output buffers to the disk.
        fd.sync();
        // Perform a non-critical write operation where synchronization isn't necessary.
        raf.write(666);
        // Do other work.
        // Close the file, emptying output buffers to the disk.
        raf.close();
    }
}
