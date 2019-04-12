package com.jasu.nio._12_NIO2._02_Files.ManageAttribute;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author @Jasu
 * @date 2018-08-30 17:19
 */
public class DFAVDemo1 {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("E:\\SpringSourceCode\\src\\main\\java\\com\\jasu\\nio\\_12_NIO2\\_02_Files\\DFAVDemo1.java");
        System.out.printf("Is archive: %b%n",
                Files.getAttribute(path, "dos:archive"));
        System.out.printf("Is hidden: %b%n",
                Files.getAttribute(path, "dos:hidden"));
        System.out.printf("Is readonly: %b%n",
                Files.getAttribute(path, "dos:readonly"));
        System.out.printf("Is system: %b%n",
                Files.getAttribute(path, "dos:system"));

        Files.setAttribute(path, "dos:system", true);
        System.out.printf("Is system: %s%n",
                Files.getAttribute(path, "dos:system"));
    }
}
